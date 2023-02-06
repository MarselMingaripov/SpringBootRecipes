package min.mars.springbootrecipes.service;

import min.mars.springbootrecipes.entity.Recipe;

import java.util.Map;

public interface RecipeService {

    Recipe addRecipe(Recipe recipe);
    Recipe getRecipe(long id);

    Map<Long, Recipe> showAllRecipes();
}
