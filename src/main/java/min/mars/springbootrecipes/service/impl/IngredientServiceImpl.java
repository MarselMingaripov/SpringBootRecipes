package min.mars.springbootrecipes.service.impl;

import min.mars.springbootrecipes.entity.Ingredient;
import min.mars.springbootrecipes.service.IngredientService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {

    private static long idIngredient = 0;

    private Map<Long, Ingredient> ingredientMap = new HashMap<>();

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        idIngredient++;
        return ingredientMap.put(idIngredient, ingredient);
    }

    @Override
    public Ingredient getIngredient(long id) {
        return ingredientMap.get(id);
    }

    @Override
    public Map<Long, Ingredient> showAll(){
        return ingredientMap;
    }

    @Override
    public void deleteIngredient(Long idIngredient){
        ingredientMap.remove(idIngredient);
    }

    @Override
    public Ingredient updateIngredient(Long id, Ingredient ingredient){
        if (ingredientMap.containsKey(id)){
            return ingredientMap.put(id, ingredient);
        }
        return null;
    }
}
