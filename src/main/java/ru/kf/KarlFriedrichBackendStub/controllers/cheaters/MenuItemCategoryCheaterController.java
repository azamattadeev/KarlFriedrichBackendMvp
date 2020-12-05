package ru.kf.KarlFriedrichBackendStub.controllers.cheaters;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kf.KarlFriedrichBackendStub.entities.MenuItemCategory;
import ru.kf.KarlFriedrichBackendStub.repositories.MenuItemCategoryRepository;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cheaters/menu-item-category/")
public class MenuItemCategoryCheaterController {
    private final MenuItemCategoryRepository repository;

    public MenuItemCategoryCheaterController(MenuItemCategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<MenuItemCategory>> getAllCategories() {
        List<MenuItemCategory> categoriesList = repository.findAll();
        return ResponseEntity.ok(categoriesList);
    }

    @GetMapping("{id}")
    public ResponseEntity<MenuItemCategory> getConfirmationData(@PathVariable Long id) {
        MenuItemCategory menuItemCategory = repository.findById(id).orElse(null);
        return (menuItemCategory != null)
                ? ResponseEntity.ok(menuItemCategory)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> addCategory(@RequestBody MenuItemCategory menuItemCategory) {
        if (repository.findMenuItemCategoryByName(menuItemCategory.getName()) != null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        menuItemCategory = repository.save(menuItemCategory);
        return ResponseEntity.created(URI.create("/cheaters/table/" + menuItemCategory.getId())).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable Long id, @RequestBody MenuItemCategory category) {
        category.setId(id);
        repository.save(category);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
