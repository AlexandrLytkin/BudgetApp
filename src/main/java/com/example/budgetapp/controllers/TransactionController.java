package com.example.budgetapp.controllers;

import com.example.budgetapp.model.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.budgetapp.model.Transaction;
import com.example.budgetapp.services.BudgetService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Month;

@RestController
@RequestMapping("/transaction")
@Tag(name = "Транзакции", description = "CRUD операции и другие эндпоинты для работы с транзакциями")
public class TransactionController {

    private final BudgetService budgetService;

    public TransactionController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public ResponseEntity<Long> addTransaction(@RequestBody Transaction transaction) {
        long id = budgetService.addTransaction(transaction);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable long id) {
        Transaction transaction = budgetService.getTransaction(id);
        if (transaction == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transaction);
    }

    @GetMapping
    @Operation(
            summary = "Поиск транзакий по месяцу и/или котегории",
            description = "Можно искать по одному параметру, обоим или вообще без параметров"
    )
    @Parameters(value = {
            @Parameter(name = "month", example = "Декабрь"
            )
    }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Транзакции были найдены",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Transaction.class))
                            )
                    }
            )
    })
    public ResponseEntity<Transaction> getAllTransaction(@RequestParam(required = false) Month month,
                                                         @RequestParam(required = false) Category category) {
        return null;
    }

    @GetMapping("/byMonth/{month}")
    public ResponseEntity<Object> getTransactionByMonth(@PathVariable Month month) {
        try {
            Path path = budgetService.createMonthlyReport(month); //1 вызываем метод буджет сервиса, плучаем путь к нашему файлу Path path
            if (Files.size(path) == 0) { // 2 если файл пустой
                return ResponseEntity.noContent().build(); // 3 возвращаем noConten с 204 ошибкой приложение не ломается
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile())); //4если файл не пустой, то мы берем у этого файла, входной поток new FileInputStream(path.toFile()) заворачиваем его в InputStreamResource
            return ResponseEntity.ok() //5 формируем Http ответ ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN) // 6 указываем тип контента обычный текстовый файл TEXT_PLAIN
                    .contentLength(Files.size(path)) // 7 указываем размер текстового файла
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + month + " -report.txt \"") // 8 ууказываем название текстового файла, объязательно указываем -report.txt, чтобы открывался во всех случаях
                    .body(resource); // 9 и отправляем его на клиент
        } catch (IOException e) { // 10 в случае какой либо ошибки
            e.printStackTrace(); // 11 печатаем в консоль нашу информацию
            return ResponseEntity.internalServerError().body(e.toString()); // 12 а также отправляем эту информацию с кодом 500 на клиент internalServerError().body
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Transaction> editTransaction(@PathVariable long id, @RequestBody Transaction transaction) {
        Transaction transacrtion = budgetService.editTransaction(id, transaction);
        if (transaction == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable long id) {
        if (budgetService.deleteTransaction(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllTransaction() {
        budgetService.deleteAllTransaction();
        return ResponseEntity.ok().build();
    }
}
