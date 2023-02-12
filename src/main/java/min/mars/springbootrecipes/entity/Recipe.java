package min.mars.springbootrecipes.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Recipe {

    private String name;
    private int preparingTimeInMinutes;
    private List<Ingredient> ingredientList;
    private List<Step> preparingSteps;

    public Recipe(String name, int preparingTimeInMinutes, List<Ingredient> ingredientList, List<Step> preparingSteps) {
        this.name = name;
        if (preparingTimeInMinutes > 0) {
            this.preparingTimeInMinutes = preparingTimeInMinutes;
        } else {
            this.preparingTimeInMinutes = Math.abs(preparingTimeInMinutes);
        }
        this.ingredientList = ingredientList;
        this.preparingSteps = preparingSteps;
    }
}
