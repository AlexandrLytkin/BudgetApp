package com.example.budgetapp.services.impl;

import com.example.budgetapp.services.BudgetService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BudgetServiceImpl implements BudgetService {

    public static final int SALARY = 20_000;
//    public static final int AVG_SALARY = (10000 + 10000 + 10000 + 10000 + 10000 + 15000 + 15000 + 15000 + 15000 + 15000 + 15000 + 20000) / 12;
    public static final int AVG_SALARY = SALARY;
    public static final double AVG_DAYS = 29.3;


    @Override
    public int getDailyBudget() {
        return SALARY / 30;
    }

    @Override
    public int getBalance() {
        return SALARY - LocalDate.now().getDayOfMonth() * getDailyBudget();
    }

    @Override
    public int getVacationBonus(int daysCount) {
        double avgDaySalary = AVG_SALARY / AVG_DAYS;
        return (int) (daysCount * avgDaySalary);
    }
    @Override
    public int getSalaryWithVacation(int vacationDaysCount, int vacationWorkingDaysCount, int workingDaysInMonth) {
        int salary = SALARY / workingDaysInMonth * (workingDaysInMonth - vacationWorkingDaysCount);
        return salary + getVacationBonus(vacationDaysCount);
    }
}
