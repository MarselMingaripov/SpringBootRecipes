package min.mars.springbootrecipes.service.impl;

import min.mars.springbootrecipes.entity.Recipe;
import min.mars.springbootrecipes.service.RecipeService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

}
