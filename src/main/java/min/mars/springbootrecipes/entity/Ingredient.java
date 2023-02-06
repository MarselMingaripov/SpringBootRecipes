package min.mars.springbootrecipes.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Ingredient {

    private String name;
    private int count;
    private String measureUnit;

    public Ingredient(String name, int count, String measureUnit) {
        this.name = name;
        if (count > 0) {
            this.count = count;
        } else {
            this.count = Math.abs(count);
        }
        this.measureUnit = measureUnit;
    }
}
