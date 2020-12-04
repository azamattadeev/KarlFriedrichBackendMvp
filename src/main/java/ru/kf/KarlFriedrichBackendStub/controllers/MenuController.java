package ru.kf.KarlFriedrichBackendStub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kf.KarlFriedrichBackendStub.dto.outgoing.MenuItemPreviewDto;
import ru.kf.KarlFriedrichBackendStub.services.MenuService;

import java.util.List;

@RestController
public class MenuController {
    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/menu")
    public List<MenuItemPreviewDto> getMenu() {
        return MenuItemPreviewDto.createListFromMenuItems(menuService.getMenu());
    }

}
