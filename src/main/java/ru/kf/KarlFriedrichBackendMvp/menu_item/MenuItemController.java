package ru.kf.KarlFriedrichBackendMvp.menu_item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

@RestController
@Validated
public class MenuItemController {
    private final MenuItemService menuItemService;

    @Autowired
    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping("/menu-item/{id}")
    public ResponseEntity<MenuItemDto> getMenuItem(@PathVariable @Positive Long id) {
        MenuItem menuItem = menuItemService.getMenuItemById(id);
        return (menuItem != null)
                ? ResponseEntity.ok(MenuItemDto.createFromMenuItem(menuItem))
                : ResponseEntity.notFound().build();
    }

}
