package min.mars.springbootrecipes.service;

import min.mars.springbootrecipes.entity.Ingredient;
import min.mars.springbootrecipes.entity.Recipe;

import java.util.Map;

public interface IngredientService {
    Ingredient addIngredient(Ingredient ingredient);
    Ingredient getIngredient(long id);

    Map<Long, Ingredient> showAll();

    void deleteIngredient(Long idIngredient);

    Ingredient updateIngredient(Long id, Ingredient ingredient);
}
