package min.mars.springbootrecipes.service.impl;

import min.mars.springbootrecipes.service.FilesIngredientService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesIngredientServiceImpl implements FilesIngredientService {

    @Value("${path.to.file}")
    private String pathToFile;

    @Value("${name.of.ingredient.file}")
    private String nameOfIngredientFile;

    @Override
    public boolean saveToFile(String json){
        try {
            cleanDataFile();
            Files.writeString(Path.of(pathToFile, nameOfIngredientFile), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readFromFile(){
        try {
            return Files.readString(Path.of(pathToFile, nameOfIngredientFile));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private boolean cleanDataFile(){
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
}
