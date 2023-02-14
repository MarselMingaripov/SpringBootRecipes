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
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
                    description = "Рецепт добавлен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Recipe.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Ошибка валидации рецепта"
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
                                    schema = @Schema(implementation = Recipe.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ошибка поиска рецепта по ид"
            )
    })
    public ResponseEntity<Recipe> showRecipe(@PathVariable Long id) {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe != null) {
            return ResponseEntity.ok().body(recipe);
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
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ошибка поиска всех рецептов"
            )
    })
    public ResponseEntity<Map<Long, Recipe>> showAll() {
        Map<Long, Recipe> recipeMap = recipeService.showAllRecipes();
        if (recipeMap != null) {
            return ResponseEntity.ok().body(recipeMap);
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
                    description = "Удален рецепт",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Recipe.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ошибка удаления рецепта по ид"
            )
    })
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable Long id) {
        Recipe recipe = recipeService.deleteRecipe(id);
        if (recipe != null) {
            return ResponseEntity.ok().body(recipe);
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
                                    schema = @Schema(implementation = Recipe.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Ошибка валидации рецепта"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ошибка обновления рецепта по ид"
            )
    })
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {

        Recipe recipe1 = recipeService.updateRecipe(id, recipe);
        if (recipe != null) {
            return ResponseEntity.ok().body(recipe1);
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
                                    schema = @Schema(implementation = Recipe.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ошибка поиска рецепта по ид ингредиента"
            )
    })
    public ResponseEntity<Recipe> findRecipeByIngredientId(@PathVariable int recipeId) {
        return ResponseEntity.ok(recipeService.findRecipeByIngredientId(recipeId));
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
                                    schema = @Schema(implementation = Recipe.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ошибка поиска рецепта по имени ингредиента"
            )
    })
    public ResponseEntity<List<Recipe>> findByIngredients(@RequestParam String ingredient) {
        return ResponseEntity.ok(recipeService.findByIngredients(ingredient));
    }

    @GetMapping("/download/recipes")
    @Operation(
            summary = "Скачать файл",
            description = "скачать файл отформатированный для удобного чтения"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "файл сформирован и готов к скачиванию"
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "файл пуст, нет сохраненных рецептов"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "ошибка со стороны сервера"
            )
    })
    public ResponseEntity<Object> download() {
        try {
            Path path = recipeService.createRecipeFileByTemplate();
            if (Files.size(path) != 0) {
                InputStreamResource ios = new InputStreamResource(new FileInputStream(path.toFile()));
                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .contentLength(Files.size(path))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "recipe.txt\"")
                        .body(ios);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
