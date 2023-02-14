package min.mars.springbootrecipes.service.impl;

import min.mars.springbootrecipes.service.FilesService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesServiceImpl implements FilesService {

    @Value("${path.to.file}")
    private String pathToFile;

    @Value("${name.of.recipes.file}")
    private String nameOfRecipeFile;

    @Value("${name.of.ingredient.file}")
    private String nameOfIngredientFile;

    @Value("${name.of.template.recipes}")
    private String nameOfTemplateFile;


    @Override
    public boolean saveRecipeToFile(String json) {
        try {
            cleanRecipeDataFile();
            Files.writeString(Path.of(pathToFile, nameOfRecipeFile), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readRecipeFromFile() {
        try {
            return Files.readString(Path.of(pathToFile, nameOfRecipeFile));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean saveIngredientToFile(String json) {
        try {
            cleanIngredientDataFile();
            Files.writeString(Path.of(pathToFile, nameOfIngredientFile), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readIngredientFromFile() {
        try {
            return Files.readString(Path.of(pathToFile, nameOfIngredientFile));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean cleanRecipeDataFile() {
        try {
            Path path = Path.of(pathToFile, nameOfRecipeFile);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean cleanIngredientDataFile() {
        try {
            Path path = Path.of(pathToFile, nameOfIngredientFile);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public File getRecipeDataFile() {
        return new File(pathToFile + "/" + nameOfRecipeFile);
    }

    @Override
    public File getIngredientDataFile() {
        return new File(pathToFile + "/" + nameOfIngredientFile);
    }

    @Override
    public ResponseEntity<Void> getVoidResponseEntity(@RequestParam MultipartFile multipartFile, File dataFile) {
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(multipartFile.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public Path returnPath(){
        Path path = Path.of(pathToFile, nameOfTemplateFile);
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}
