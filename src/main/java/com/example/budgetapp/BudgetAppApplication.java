package com.example.budgetapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BudgetAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BudgetAppApplication.class, args);
    }
//    пример:
//    http://localhost:8080/budget/vacation/salary?vacationDays=14&workingDays=21&vacWorkDays=10
}
