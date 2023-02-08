package min.mars.springbootrecipes.service;

import min.mars.springbootrecipes.entity.Ingredient;

import java.util.Map;

public interface IngredientService {
    Ingredient addIngredient(Ingredient ingredient);
    Ingredient getIngredient(long id);

    Map<Long, Ingredient> showAll();

    Object deleteIngredient(Long idIngredient);

    Ingredient updateIngredient(Long id, Ingredient ingredient);
}
