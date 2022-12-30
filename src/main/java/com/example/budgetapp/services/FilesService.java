package com.example.budgetapp.services;

import java.io.File;
import java.nio.file.Path;

public interface FilesService {

    boolean safeToFile(String json);

    String readFromFile();

    File getDataFile();

    Path createTempFile(String suffix);

    boolean cleanDataFile();
}
