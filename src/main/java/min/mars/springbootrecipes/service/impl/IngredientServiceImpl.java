package min.mars.springbootrecipes.service.impl;

import min.mars.springbootrecipes.entity.Ingredient;
import min.mars.springbootrecipes.exception.ValidationException;
import min.mars.springbootrecipes.service.IngredientService;
import min.mars.springbootrecipes.service.ValidationService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {

    private static long idIngredient = 0;

    private final Map<Long, Ingredient> ingredientMap = new HashMap<>();

    private final ValidationService validationService;

    public IngredientServiceImpl(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        if (!validationService.validateIngredient(ingredient)){
            throw new ValidationException(ingredient.toString());
        }
        idIngredient++;
        return ingredientMap.put(idIngredient, ingredient);
    }

    @Override
    public Ingredient getIngredient(long id) {
        if (!ingredientMap.containsKey(id)) {
            return ingredientMap.get(id);
        } else {
            return null;
        }
    }

    @Override
    public Map<Long, Ingredient> showAll() {
            return ingredientMap;
    }

    @Override
    public Ingredient deleteIngredient(Long idIngredient) {
        if (ingredientMap.containsKey(idIngredient)) {
            return ingredientMap.remove(idIngredient);
        } else {
            return null;
        }
    }

    @Override
    public Ingredient updateIngredient(Long id, Ingredient ingredient) {
        if (ingredientMap.containsKey(id)) {
            return ingredientMap.put(id, ingredient);
        }
        return null;
    }
}
