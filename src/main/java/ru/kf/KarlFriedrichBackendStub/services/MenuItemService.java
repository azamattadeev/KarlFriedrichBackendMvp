package ru.kf.KarlFriedrichBackendStub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kf.KarlFriedrichBackendStub.entities.MenuItem;
import ru.kf.KarlFriedrichBackendStub.repositories.MenuItemRepository;

@Service
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id).orElse(null);
    }

}
