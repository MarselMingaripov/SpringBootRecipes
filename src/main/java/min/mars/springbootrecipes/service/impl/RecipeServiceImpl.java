package min.mars.springbootrecipes.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import min.mars.springbootrecipes.entity.Ingredient;
import min.mars.springbootrecipes.entity.Recipe;
import min.mars.springbootrecipes.exception.ValidationException;
import min.mars.springbootrecipes.service.FilesService;
import min.mars.springbootrecipes.service.RecipeService;
import min.mars.springbootrecipes.service.ValidationService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {

    private static long recipeId = 0;

    private Map<Long, Recipe> recipeMap = new HashMap<>();

    private final ValidationService validationService;

    private final FilesService filesService;

    public RecipeServiceImpl(ValidationService validationService, FilesService filesService) {
        this.validationService = validationService;
        this.filesService = filesService;
    }

    @PostConstruct
    public void init() {
        readFromFile();
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        if (!validationService.validateRecipe(recipe)) {
            throw new ValidationException(recipe.toString());
        }
        recipeId++;
        Recipe recipe1 = recipeMap.put(recipeId, recipe);
        saveToFile();
        return recipe1;
    }

    @Override
    public Recipe getRecipe(long id) {
        if (recipeMap.containsKey(id)) {
            return recipeMap.get(id);
        } else {
            return null;
        }
    }

    @Override
    public Map<Long, Recipe> showAllRecipes() {
        return recipeMap;
    }

    @Override
    public Recipe deleteRecipe(Long recipeId) {
        if (recipeMap.containsKey(recipeId)) {
            Recipe recipe = recipeMap.remove(recipeId);
            saveToFile();
            return recipe;
        } else {
            return null;
        }
    }

    @Override
    public Recipe updateRecipe(Long id, Recipe recipe) {
        if (recipeMap.containsKey(id)) {
            if (validationService.validateRecipe(recipe)) {
                Recipe recipe1 = recipeMap.put(id, recipe);
                saveToFile();
                return recipe1;
            }
        }
        return null;
    }

    @Override
    public Recipe findRecipeByIngredientId(int id) {
        for (Recipe recipe : recipeMap.values()) {
            if (recipe.getIngredientList().get(id) != null) {
                return recipe;
            }
        }
        return null;
    }

    @Override
    public List<Recipe> findByIngredients(String ingredient) {
        List<Recipe> recipeList = new ArrayList<>();
        for (Recipe recipe : recipeMap.values()) {
            for (Ingredient ingredient1 : recipe.getIngredientList()) {
                if (ingredient1.getName().equals(ingredient)) {
                    recipeList.add(recipe);
                }
            }
        }
        return recipeList;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipeMap);
            filesService.saveRecipeToFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        try {
            String json = filesService.readRecipeFromFile();
            recipeMap = new ObjectMapper().readValue(json, new TypeReference<Map<Long, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
