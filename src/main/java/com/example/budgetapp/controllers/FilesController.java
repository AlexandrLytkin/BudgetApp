package com.example.budgetapp.controllers;

import com.example.budgetapp.services.FilesService;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/files")
public class FilesController {

    private final FilesService filesService;

    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }

    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> downloadDataFIle() throws FileNotFoundException { // для загрузки файла
        File file = filesService.getDataFile();

        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON) // APPLICATION_JSON говорить что это поток json
//                    .contentType(MediaType.APPLICATION_OCTET_STREAM) // APPLICATION_OCTET_STREAM говорить что это поток байт не json
                    .contentLength(file.length()) //конт ленгс проверяет сколько байт получил
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"TransactionsLog.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build(); //noContent().build(); это 204 статус
        }
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadDataFile(@RequestParam MultipartFile file) { // для выгрузки файла
        filesService.cleanDataFile();
        File dataFile = filesService.getDataFile();

        try (FileOutputStream fos = new FileOutputStream(dataFile)) {

            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}