package min.mars.springbootrecipes.service;

public interface FilesRecipeService {

    boolean saveToFile(String json);

    String readFromFile();
}
