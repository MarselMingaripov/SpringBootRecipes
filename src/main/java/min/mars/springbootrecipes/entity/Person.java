package min.mars.springbootrecipes.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class Person {

    private String name;
    private String projectName;
    private LocalDate localDate;
    private String projectDescription;

    public Person(String name, String projectName, String projectDescription) {
        this.name = name;
        this.projectName = projectName;
        this.localDate = localDate.now();
        this.projectDescription = projectDescription;
    }
}
