package ru.kf.KarlFriedrichBackendMvp.table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@RestController
@Validated
public class TableController {
    private final TableService tableService;

    @Autowired
    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping("/table/{id}")
    public ResponseEntity<Table> getTableById(@PathVariable @Positive Long id) {
        Table table = tableService.getTableById(id);
        return (table != null)
                ? ResponseEntity.ok(table)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/table")
    public ResponseEntity<Table> getTableByQR(@RequestParam @NotBlank String qrCode) {
        Table table = tableService.getTableByQR(qrCode);
        return (table != null)
                ? ResponseEntity.ok(table)
                : ResponseEntity.notFound().build();
    }

}
