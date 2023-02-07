package min.mars.springbootrecipes.controller;

import min.mars.springbootrecipes.entity.Ingredient;
import min.mars.springbootrecipes.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController()
@RequestMapping("/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping("/add")
    public ResponseEntity addIngredient(@RequestBody Ingredient ingredient){
        try {
            ingredientService.addIngredient(ingredient);
            return ResponseEntity.ok().build();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return (ResponseEntity) ResponseEntity.EMPTY;
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<Ingredient> showIngredient(@PathVariable Long id){
        return ResponseEntity.ok(ingredientService.getIngredient(id));
    }
    @GetMapping("show/all")
    public Map<Long, Ingredient> showAll(){
        return ingredientService.showAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id){
        ingredientService.deleteIngredient(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable Long id, @RequestBody Ingredient ingredient){
        ingredientService.updateIngredient(id, ingredient);
        return ResponseEntity.ok(ingredient);
    }
}
