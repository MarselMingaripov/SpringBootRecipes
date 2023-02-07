package min.mars.springbootrecipes.service.impl;

import min.mars.springbootrecipes.entity.Ingredient;
import min.mars.springbootrecipes.entity.Recipe;
import min.mars.springbootrecipes.service.RecipeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {

    private static long recipeId = 0;

    private Map<Long, Recipe> recipeMap = new HashMap<>();

    @Override
    public Recipe addRecipe(Recipe recipe) {
        recipeId++;
        return recipeMap.put(recipeId, recipe);
    }

    @Override
    public Recipe getRecipe(long id) {
        if (!recipeMap.containsKey(id)){
            throw new IllegalArgumentException("Ид не найден");
        }
        return recipeMap.get(id);
    }

    @Override
    public Map<Long, Recipe> showAllRecipes(){
        return recipeMap;
    }

    @Override
    public void deleteRecipe(Long recipeId){
        recipeMap.remove(recipeId);
    }

    @Override
    public Recipe updateRecipe(Long id, Recipe recipe){
        if (recipeMap.containsKey(id)){
            return recipeMap.put(id, recipe);
        }
        return null;
    }

    @Override
    public Recipe findRecipeByIngredientId(int id){
        for (Recipe recipe : recipeMap.values()) {
            if (recipe.getIngredientList().get(id) != null){
                return recipe;
            }
        }
        return null;
    }

    @Override
    public List<Recipe> findByIngredients(String ingredient){
        List<Recipe> recipeList = new ArrayList<>();
        for (Recipe recipe : recipeMap.values()) {
            for (Ingredient ingredient1 : recipe.getIngredientList()) {
                if (ingredient1.getName().equals(ingredient)){
                    recipeList.add(recipe);
                }
            }
        }
        return recipeList;
    }
}
