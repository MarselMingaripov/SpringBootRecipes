package min.mars.springbootrecipes.controller;

import min.mars.springbootrecipes.entity.Recipe;
import min.mars.springbootrecipes.service.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping()
    public void addRecipe(@RequestBody Recipe recipe){
        recipeService.addRecipe(recipe);
    }

    @GetMapping("/{id}")
    public Recipe showRecipe(@PathVariable Long id){
        return recipeService.getRecipe(id);
    }

    @GetMapping("/")
    public Map<Long, Recipe> showAll(){
        return recipeService.showAllRecipes();
    }
}
