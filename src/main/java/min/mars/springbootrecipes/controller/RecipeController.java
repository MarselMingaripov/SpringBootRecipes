package min.mars.springbootrecipes.controller;

import min.mars.springbootrecipes.entity.Ingredient;
import min.mars.springbootrecipes.entity.Recipe;
import min.mars.springbootrecipes.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/add")
    public ResponseEntity addRecipe(@RequestBody Recipe recipe){
        try {
            recipeService.addRecipe(recipe);
            return ResponseEntity.ok().build();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return (ResponseEntity) ResponseEntity.EMPTY;
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<Recipe> showRecipe(@PathVariable Long id){
        return ResponseEntity.ok().body(recipeService.getRecipe(id));
    }

    @GetMapping("show/all")
    public Map<Long, Recipe> showAll(){
        return recipeService.showAllRecipes();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id){
        recipeService.deleteRecipe(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe){
        recipeService.updateRecipe(id, recipe);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/show-by-recipe/{recipeId}")
    public ResponseEntity<Recipe> findRecipeByIngredientId(@PathVariable int recipeId){
        return ResponseEntity.ok(recipeService.findRecipeByIngredientId(recipeId));
    }

    @GetMapping("/find-by-ingredients")
    public List<Recipe> findByIngredients(@RequestParam String ingredient){
        return recipeService.findByIngredients(ingredient);
    }
}
