package com.example.budgetapp.services;

import com.example.budgetapp.model.Transaction;

public interface BudgetService {

    int getDailyBudget();

    int getBalance();

    void addTransaction(Transaction transaction);

    Transaction getTransaction(long id);

    int getDailyBalance();

    int getAllSpend();

    int getVacationBonus(int daysCount);

    int getSalaryWithVacation(int vacationDaysCount, int vacationWorkingDaysCount, int workingDaysInMonth);
}
