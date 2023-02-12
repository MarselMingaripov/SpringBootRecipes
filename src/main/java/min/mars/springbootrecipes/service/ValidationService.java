package min.mars.springbootrecipes.service;

import min.mars.springbootrecipes.entity.Ingredient;
import min.mars.springbootrecipes.entity.Recipe;

public interface ValidationService {

    boolean validateRecipe(Recipe recipe);

    boolean validateIngredient(Ingredient ingredient);
}
