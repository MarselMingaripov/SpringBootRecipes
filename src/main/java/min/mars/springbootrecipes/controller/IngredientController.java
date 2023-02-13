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
                    description = "Добавлен ингредиент",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Ingredient.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ошибка добавления ингредиента"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Ошибка валидации ингредиента"
            )
    })
    public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient ingredient) {
        Ingredient ingredient1 = ingredientService.addIngredient(ingredient);
        return ResponseEntity.ok().body(ingredient1);
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
                                    schema = @Schema(implementation = Ingredient.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ошибка получения ингредиента по ид"
            )
    })
    public ResponseEntity<Ingredient> showIngredient(@PathVariable Long id) {
        Ingredient ingredient = ingredientService.getIngredient(id);
        if (ingredient != null) {
            return ResponseEntity.ok(ingredient);
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
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ошибка поиска всех ингредиентов"
            )
    })
    public ResponseEntity<Map<Long, Ingredient>> showAll() {
            return ResponseEntity.ok(ingredientService.showAll());
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление ингредиента по идентификатору",
            description = "Идентификатор = id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Удален ингредиент",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Ingredient.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ошибка удаления ингредиента по ид"
            )
    })
    public ResponseEntity<Object> deleteIngredient(@PathVariable Long id) {
        Object obj = ingredientService.deleteIngredient(id);
        if (obj != null) {
            return ResponseEntity.ok(obj);
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
                                    schema = @Schema(implementation = Ingredient.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Ошибка валидации ингредиента"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ошибка обновления ингредиента по ид"
            )
    })
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable Long id, @RequestBody Ingredient ingredient) {
        Ingredient ingredient1 = ingredientService.updateIngredient(id, ingredient);
        if (ingredient != null) {
            return ResponseEntity.ok(ingredient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
