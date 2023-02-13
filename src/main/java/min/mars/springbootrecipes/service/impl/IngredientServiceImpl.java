package min.mars.springbootrecipes.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import min.mars.springbootrecipes.entity.Ingredient;
import min.mars.springbootrecipes.exception.ValidationException;
import min.mars.springbootrecipes.service.FilesService;
import min.mars.springbootrecipes.service.IngredientService;
import min.mars.springbootrecipes.service.ValidationService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {

    private static long idIngredient = 0;

    private Map<Long, Ingredient> ingredientMap = new HashMap<>();

    private final ValidationService validationService;

    private final FilesService filesService;

    public IngredientServiceImpl(ValidationService validationService, FilesService filesService) {
        this.validationService = validationService;
        this.filesService = filesService;
    }

    @PostConstruct
    public void init() {
        readFromFile();
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        if (!validationService.validateIngredient(ingredient)) {
            throw new ValidationException(ingredient.toString());
        }
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
            Ingredient ingredient = ingredientMap.remove(idIngredient);
            saveToFile();
            return ingredient;
        } else {
            return null;
        }
    }

    @Override
    public Ingredient updateIngredient(Long id, Ingredient ingredient) {
        if (ingredientMap.containsKey(id)) {
            if (validationService.validateIngredient(ingredient)) {
                Ingredient ingredient1 = ingredientMap.put(id, ingredient);
                saveToFile();
                return ingredient1;
            }
        }
        return null;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientMap);
            filesService.saveIngredientToFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        try {
            String json = filesService.readIngredientFromFile();
            ingredientMap = new ObjectMapper().readValue(json, new TypeReference<Map<Long, Ingredient>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
