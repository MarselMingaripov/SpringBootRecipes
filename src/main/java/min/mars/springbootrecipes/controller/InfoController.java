package min.mars.springbootrecipes.controller;

import min.mars.springbootrecipes.entity.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @GetMapping("/")
    public String appIsExecutedPage(){
        return "Приложение запущено!";
    }

    @GetMapping("/info")
    public String infoPage(){
        return new Person("Marsel", "SpringBootRecipes", "Приложение для сайта рецептов").toString();
    }
}
