package min.mars.springbootrecipes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import min.mars.springbootrecipes.entity.Recipe;
import min.mars.springbootrecipes.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recipe")
@Tag(name = "Рецепты", description = "Эндпоинты для работы с рецептами")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/")
    @Operation(
            summary = "Добавление рецепта",
            description = "json"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт добавлен"
            )
    })
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe) {
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение рецепта по идентификатору",
            description = "Идентификатор = id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Найден рецепт",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            )
    })
    public ResponseEntity<Recipe> showRecipe(@PathVariable Long id) {
        if (recipeService.getRecipe(id) != null) {
            return ResponseEntity.ok().body(recipeService.getRecipe(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    @Operation(
            summary = "Получение всех рецептов",
            description = "Список рецептов"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепты найдены",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            )
    })
    public ResponseEntity<Map<Long, Recipe>> showAll() {
        if (recipeService.showAllRecipes() != null){
            return ResponseEntity.ok(recipeService.showAllRecipes());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление рецепта по идентификатору",
            description = "Идентификатор = id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Удален рецепт"
            )
    })
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable Long id) {
        if (recipeService.deleteRecipe(id) != null) {
            return ResponseEntity.ok(recipeService.deleteRecipe(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновление рецепта по идентификатору",
            description = "Идентификатор = id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Обновлен рецепт",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            )
    })
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
        if (recipeService.updateRecipe(id, recipe) != null) {
            return ResponseEntity.ok(recipeService.updateRecipe(id, recipe));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/show-by-recipe/{recipeId}")
    @Operation(
            summary = "Получение рецепта по идентификатору ингредиента",
            description = "Идентификатор = id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Найден Рецепт",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            )
    })
    public ResponseEntity<Recipe> findRecipeByIngredientId(@PathVariable int recipeId) {
        if (recipeService.findRecipeByIngredientId(recipeId) != null) {
            return ResponseEntity.ok(recipeService.findRecipeByIngredientId(recipeId));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/find-by-ingredients")
    @Operation(
            summary = "Получение рецепта по имени ингредиента",
            description = "поле 'name' ингредиента"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Найден рецепт",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            )
    })
    public ResponseEntity<List<Recipe>> findByIngredients(@RequestParam String ingredient) {
        if (recipeService.findByIngredients(ingredient) != null) {
            return ResponseEntity.ok(recipeService.findByIngredients(ingredient));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
