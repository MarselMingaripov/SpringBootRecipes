package min.mars.springbootrecipes.service.impl;

import min.mars.springbootrecipes.service.FilesRecipeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesRecipeServiceImpl implements FilesRecipeService {

    @Value("${path.to.file}")
    private String pathToFile;

    @Value("${name.of.recipes.file}")
    private String nameOfRecipeFile;


    @Override
    public boolean saveToFile(String json){
        try {
            cleanDataFile();
            Files.writeString(Path.of(pathToFile, nameOfRecipeFile), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readFromFile(){
        try {
            return Files.readString(Path.of(pathToFile, nameOfRecipeFile));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private boolean cleanDataFile(){
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
}
