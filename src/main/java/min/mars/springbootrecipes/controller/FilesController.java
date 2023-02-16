package min.mars.springbootrecipes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import min.mars.springbootrecipes.service.FilesService;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/files")
public class FilesController {

    private final FilesService filesService;

    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }

    @GetMapping("/export/recipes")
    @Operation(
            summary = "Скачать файл с рецептами",
            description = "позволяет сохранить файл на компьютер"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Файл сохранен"
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Запрос корректен, но не найден файл на сервере"
            )
    })
    public ResponseEntity<InputStreamResource> downloadRecipeDataFile() throws FileNotFoundException {
        File file = filesService.getRecipeDataFile();

        if (file.exists()){
            InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RecipeLog.json\"")
                    .body(inputStreamResource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/export/ingredients")
    @Operation(
            summary = "Скачать файл с ингредиентами",
            description = "позволяет сохранить файл на компьютер"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Файл сохранен"
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Запрос корректен, но не найден файл на сервере"
            )
    })
    public ResponseEntity<InputStreamResource> downloadIngredientDataFile() throws FileNotFoundException {
        File file = filesService.getIngredientDataFile();

        if (file.exists()){
            InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"IngredientLog.json\"")
                    .body(inputStreamResource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/import/recipe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Загрузить файл с рецептами",
            description = "позволяет загрузить файл на сервер"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Файл загружен"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Ошибка со стороны сервера"
            )
    })
    public ResponseEntity<Void> uploadRecipeFile(@RequestParam MultipartFile multipartFile){
        filesService.cleanRecipeDataFile();
        File dataFile = filesService.getRecipeDataFile();

        return getVoidResponseEntity(multipartFile, dataFile);
    }

    @PostMapping(value = "/import/ingredient", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Загрузить файл с ингредиентами",
            description = "позволяет загрузить файл на сервер"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Файл загружен"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Ошибка со стороны сервера"
            )
    })
    public ResponseEntity<Void> uploadIngredientFile(@RequestParam MultipartFile multipartFile){
        filesService.cleanIngredientDataFile();
        File dataFile = filesService.getIngredientDataFile();

        return getVoidResponseEntity(multipartFile, dataFile);
    }

    private ResponseEntity<Void> getVoidResponseEntity(MultipartFile multipartFile, File dataFile) {
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(multipartFile.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
