package com.example.budgetapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {


    @GetMapping
    public String getApplicationLaunched() {
        return "Приложение запущено!\n" +
                "добавьте в конце адрессной строки /info для получения информации ";
    }

    @GetMapping("/info")
    public String getInfo() {
        return "Лыткин Александр Александрович\n" +
                "Проект Recipes\n" +
                "Дата создания проекта 10.12.2022\n" +
                "В проекте будут рецепты, для приготовления изысканных блюд!";

    }

}
