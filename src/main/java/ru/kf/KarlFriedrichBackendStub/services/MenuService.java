package ru.kf.KarlFriedrichBackendStub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kf.KarlFriedrichBackendStub.entities.MenuItem;
import ru.kf.KarlFriedrichBackendStub.repositories.MenuItemRepository;

import java.util.List;

@Service
public class MenuService {
    private final MenuItemRepository menuItemRepository;

    @Autowired
    public MenuService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> getMenu() {
        return menuItemRepository.findAllByAccessibilityIsTrue();
    }

}
