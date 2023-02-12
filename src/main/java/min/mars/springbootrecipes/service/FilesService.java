package min.mars.springbootrecipes.service;

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
}