package ru.kf.KarlFriedrichBackendStub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.kf.KarlFriedrichBackendStub.dto.outgoing.MenuItemDto;
import ru.kf.KarlFriedrichBackendStub.entities.MenuItem;
import ru.kf.KarlFriedrichBackendStub.services.MenuItemService;

@RestController
public class MenuItemController {
    private final MenuItemService menuItemService;

    @Autowired
    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping("/menu-item/{id}")
    public ResponseEntity<MenuItemDto> getMenuItem(@PathVariable("id") Long id) {
        MenuItem menuItem = menuItemService.getMenuItemById(id);
        if (menuItem != null) {
            return ResponseEntity.ok(MenuItemDto.createFromMenuItem(menuItem));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
