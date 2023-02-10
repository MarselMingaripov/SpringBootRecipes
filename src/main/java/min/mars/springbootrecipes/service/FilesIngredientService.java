package min.mars.springbootrecipes.service;

public interface FilesIngredientService {

    boolean saveToFile(String json);

    String readFromFile();
}
