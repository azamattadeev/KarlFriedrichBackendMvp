package ru.kf.KarlFriedrichBackendStub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kf.KarlFriedrichBackendStub.entities.Table;
import ru.kf.KarlFriedrichBackendStub.services.TableService;

@RestController
public class TableController {
    public final TableService tableService;

    @Autowired
    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping("/table/{id}")
    public ResponseEntity<Table> getTableById(@PathVariable("id") Long id) {
        Table table = tableService.getTableById(id);
        if (table != null) {
            return ResponseEntity.ok(table);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/table/")
    public ResponseEntity<Table> getTableByQR(@RequestParam String qrCode) {
        Table table = tableService.getTableByQR(qrCode);
        if (table != null) {
            return ResponseEntity.ok(table);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
