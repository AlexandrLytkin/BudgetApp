package com.example.budgetapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {


    @GetMapping
    public String helloWorld() {return "Hello, web!";}

    @GetMapping("/path/to/page")
    public String page(@RequestParam String page) {
        return "Page: "+ page ;
    }
//пример:
//http://localhost:8080/budget/vacation/salary?vacationDays=14&workingDays=21&vacWorkDays=10
}
