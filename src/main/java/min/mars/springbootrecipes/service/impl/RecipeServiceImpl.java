package min.mars.springbootrecipes.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import min.mars.springbootrecipes.entity.Ingredient;
import min.mars.springbootrecipes.entity.Recipe;
import min.mars.springbootrecipes.service.FilesRecipeService;
import min.mars.springbootrecipes.service.RecipeService;
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

    private final FilesRecipeService filesRecipeService;

    public RecipeServiceImpl(FilesRecipeService filesRecipeService) {
        this.filesRecipeService = filesRecipeService;
    }

    @PostConstruct
    private void init(){

        readFromFile();
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
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
            return recipeMap.remove(recipeId);
        } else {
            return null;
        }
    }

    @Override
    public Recipe updateRecipe(Long id, Recipe recipe) {
        if (recipeMap.containsKey(id)) {
            Recipe recipe1 = recipeMap.put(id, recipe);
            saveToFile();
            return recipe1;
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

    private void saveToFile(){
        try {
            String json = new ObjectMapper().writeValueAsString(recipeMap);
            filesRecipeService.saveToFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile(){
        try {
            String json = filesRecipeService.readFromFile();
            recipeMap = new ObjectMapper().readValue(json, new TypeReference<Map<Long, Recipe>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
