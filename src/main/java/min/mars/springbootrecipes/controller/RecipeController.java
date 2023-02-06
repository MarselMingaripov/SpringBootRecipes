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

    @PostMapping("/add")
    public void addRecipe(@RequestBody Recipe recipe){
        try {
            recipeService.addRecipe(recipe);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/show/{id}")
    public Recipe showRecipe(@PathVariable Long id){
        return recipeService.getRecipe(id);
    }

    @GetMapping("show/all")
    public Map<Long, Recipe> showAll(){
        return recipeService.showAllRecipes();
    }
}
