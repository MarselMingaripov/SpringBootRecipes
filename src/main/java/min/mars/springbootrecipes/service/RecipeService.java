package min.mars.springbootrecipes.service;

import min.mars.springbootrecipes.entity.Recipe;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface RecipeService {

    Recipe addRecipe(Recipe recipe);
    Recipe getRecipe(long id);

    Map<Long, Recipe> showAllRecipes();

    Recipe deleteRecipe(Long recipeId);

    Recipe updateRecipe(Long id, Recipe recipe);

    Recipe findRecipeByIngredientId(int id);

    List<Recipe> findByIngredients(String ingredient);

    Path createRecipeFileByTemplate() throws IOException;
}
