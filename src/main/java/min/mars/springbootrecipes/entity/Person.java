package min.mars.springbootrecipes.entity;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Person {

    private String name;
    private String projectName;
    private LocalDate localDate;
    private String projectDescription;

    public Person(String name, String projectName, String projectDescription) {
        this.name = name;
        this.projectName = projectName;
        this.localDate = LocalDate.now();
        this.projectDescription = projectDescription;
    }
}
