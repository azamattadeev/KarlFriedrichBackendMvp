package ru.kf.KarlFriedrichBackendStub.controllers.cheaters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kf.KarlFriedrichBackendStub.entities.MenuItem;
import ru.kf.KarlFriedrichBackendStub.repositories.MenuItemRepository;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cheaters/menu-item/")
public class MenuItemCheaterController {
    private final MenuItemRepository repository;

    @Autowired
    public MenuItemCheaterController(MenuItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        List<MenuItem> itemsList = repository.findAll();
        return ResponseEntity.ok(itemsList);
    }

    @GetMapping("{id}")
    public ResponseEntity<MenuItem> getMenuItem(@PathVariable Long id) {
        MenuItem menuItem = repository.findById(id).orElse(null);
        return (menuItem != null)
                ? ResponseEntity.ok(menuItem)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> addMenuItem(@RequestBody MenuItem menuItem) {
        menuItem = repository.save(menuItem);
        return ResponseEntity.created(URI.create("/cheaters/table/" + menuItem.getId())).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateMenuItem(@PathVariable Long id, @RequestBody MenuItem menuItem) {
        menuItem.setId(id);
        repository.save(menuItem);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
