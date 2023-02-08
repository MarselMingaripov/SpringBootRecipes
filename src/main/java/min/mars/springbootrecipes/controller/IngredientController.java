package min.mars.springbootrecipes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import min.mars.springbootrecipes.entity.Ingredient;
import min.mars.springbootrecipes.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ingredient")
@Tag(name = "Ингредиенты", description = "Эндпоинты для работы с ингредиентами")
public class IngredientController {


    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping("/")
    @Operation(
            summary = "Добавление ингредиента",
            description = "Данные формата json"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Добавлен ингредиент"
            )
    })
    public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient ingredient) {
        ingredientService.addIngredient(ingredient);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение ингредиента по идентификатору",
            description = "Идентификатор = id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Найден ингредиент",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            )
    })
    public ResponseEntity<Ingredient> showIngredient(@PathVariable Long id) {
        if (ingredientService.getIngredient(id) != null) {
            return ResponseEntity.ok(ingredientService.getIngredient(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    @Operation(
            summary = "Получение списка ингредиентов",
            description = "Выведется все"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Найдены ингредиенты",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            )
    })
    public ResponseEntity<Map<Long, Ingredient>> showAll() {

        if (!ingredientService.showAll().isEmpty()) {
            return ResponseEntity.ok(ingredientService.showAll());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление ингредиента по идентификатору",
            description = "Идентификатор = id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Удален ингредиент"
            )
    })
    public ResponseEntity<Object> deleteIngredient(@PathVariable Long id) {
        if (ingredientService.deleteIngredient(id) != null) {
            return ResponseEntity.ok(ingredientService.deleteIngredient(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновление ингредиента по идентификатору",
            description = "Идентификатор = id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Обновлен ингредиент",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            )
    })
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable Long id, @RequestBody Ingredient ingredient) {
        if (ingredientService.updateIngredient(id, ingredient) != null) {
            return ResponseEntity.ok(ingredientService.updateIngredient(id, ingredient));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
