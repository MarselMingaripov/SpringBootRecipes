package min.mars.springbootrecipes.service.impl;

import min.mars.springbootrecipes.entity.Ingredient;
import min.mars.springbootrecipes.entity.Recipe;
import min.mars.springbootrecipes.service.ValidationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements ValidationService {
    @Override
    public boolean validateRecipe(Recipe recipe) {
        return StringUtils.isNotBlank(recipe.getName())
                && !recipe.getIngredientList().isEmpty()
                && !recipe.getPreparingSteps().isEmpty();
    }

    @Override
    public boolean validateIngredient(Ingredient ingredient) {

        return StringUtils.isNotBlank(ingredient.getName())
                && StringUtils.isNotBlank(ingredient.getMeasureUnit());
    }
}
