package min.mars.springbootrecipes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import min.mars.springbootrecipes.entity.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Здравствуйте!", description = "Информация о проекте")
public class InfoController {

    @GetMapping("/")
    @Operation(
            summary = "Выводит статус, что приложение запущено",
            description = "Никакой функциональности больше нет("
    )
    public String appIsExecutedPage(){
        return "Приложение запущено!";
    }

    @GetMapping("/info")
    @Operation(
            summary = "Выводит информацию о создателе проекта",
            description = "Передается по умолчанию созданный объект класса Person"
    )
    public String infoPage(){
        return new Person("Marsel", "SpringBootRecipes", "Приложение для сайта рецептов").toString();
    }
}
