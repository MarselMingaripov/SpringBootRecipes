package min.mars.springbootrecipes.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import min.mars.springbootrecipes.entity.Ingredient;
import min.mars.springbootrecipes.service.IngredientService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final FilesIngredientServiceImpl filesIngredientService;

    private static long idIngredient = 0;

    private Map<Long, Ingredient> ingredientMap = new HashMap<>();

    public IngredientServiceImpl(FilesIngredientServiceImpl filesIngredientService) {
        this.filesIngredientService = filesIngredientService;
    }

    @PostConstruct
    private void init(){
        readFromFile();
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        idIngredient++;
        Ingredient ingredient1 = ingredientMap.put(idIngredient, ingredient);
        saveToFile();
        return ingredient1;
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
            Ingredient ingredient1 = ingredientMap.put(id, ingredient);
            saveToFile();
            return ingredient1;
        }
        return null;
    }

    private void saveToFile(){
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientMap);
            filesIngredientService.saveToFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile(){
        try {
            String json = filesIngredientService.readFromFile();
            ingredientMap = new ObjectMapper().readValue(json, new TypeReference<Map<Long, Ingredient>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
