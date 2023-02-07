package min.mars.springbootrecipes.controller;

import min.mars.springbootrecipes.entity.Ingredient;
import min.mars.springbootrecipes.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping()
    public void addIngredient(@RequestBody Ingredient ingredient) {
        ingredientService.addIngredient(ingredient);
    }

    @GetMapping("/{id}")
    public Ingredient showIngredient(@PathVariable Long id) {
        return ingredientService.getIngredient(id);
    }

    @GetMapping("/")
    public Map<Long, Ingredient> showAll() {
        return ingredientService.showAll();
    }
}
