package ru.kf.KarlFriedrichBackendMvp.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kf.KarlFriedrichBackendMvp.menu_item.MenuItem;
import ru.kf.KarlFriedrichBackendMvp.menu_item.MenuItemRepository;

import java.util.List;

@Service
@Transactional
public class MenuService {
    private final MenuItemRepository menuItemRepository;

    @Autowired
    public MenuService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> getMenu() {
        return menuItemRepository.findAllByAvailableIsTrue();
    }

}
