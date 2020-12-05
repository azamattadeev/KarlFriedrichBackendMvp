package ru.kf.KarlFriedrichBackendStub.controllers.cheaters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kf.KarlFriedrichBackendStub.entities.Table;
import ru.kf.KarlFriedrichBackendStub.repositories.TableRepository;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cheaters/table/")
public class TableCheaterController {
    private final TableRepository tableRepository;

    @Autowired
    public TableCheaterController(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @GetMapping
    public ResponseEntity<List<Table>> getTables() {
        List<Table> tablesList = tableRepository.findAll();
        return ResponseEntity.ok(tablesList);
    }

    @GetMapping("{id}")
    public ResponseEntity<Table> getTable(@PathVariable Long id) {
        Table table = tableRepository.findById(id).orElse(null);
        return (table != null)
                ? ResponseEntity.ok(table)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> addTable(@RequestBody Table table) {
        if (tableRepository.findTableByQrCode(table.getQrCode()) != null
                || tableRepository.findTableByTableNumber(table.getTableNumber()) != null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        table = tableRepository.save(table);
        return ResponseEntity.created(URI.create("/cheaters/table/" + table.getId())).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateTable(@PathVariable Long id, @RequestBody Table table) {
        table.setId(id);
        tableRepository.save(table);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTable(@PathVariable("id") Long id) {
        tableRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
