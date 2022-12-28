package com.example.budgetapp.services;

public interface FilesService {

    boolean safeToFile(String json);

    String readFromFile();
}
