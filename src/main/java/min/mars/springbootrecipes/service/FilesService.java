package min.mars.springbootrecipes.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;

public interface FilesService {

    /**
     * сохраняет рецепт в файл
     * @param json - сохраняемый объект
     * @return
     */
    boolean saveRecipeToFile(String json);

    /**
     * считывает рецепты из файла
     * @return
     */
    String readRecipeFromFile();

    /**
     * сохраняет ингредиенты в файл
     * @param json - сохраняемый объект
     * @return
     */
    boolean saveIngredientToFile(String json);

    /**
     * считывает ингредиенты из файла
     * @return
     */
    String readIngredientFromFile();

    boolean cleanRecipeDataFile();

    boolean cleanIngredientDataFile();

    File getRecipeDataFile();

    File getIngredientDataFile();

    ResponseEntity<Void> getVoidResponseEntity(@RequestParam MultipartFile multipartFile, File dataFile);

    Path returnPath();
}