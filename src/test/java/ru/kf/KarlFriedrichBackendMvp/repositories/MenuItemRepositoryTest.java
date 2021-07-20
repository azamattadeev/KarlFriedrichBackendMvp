package ru.kf.KarlFriedrichBackendMvp.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.kf.KarlFriedrichBackendMvp.menu_item.MenuItem;
import ru.kf.KarlFriedrichBackendMvp.menu_item_category.MenuItemCategory;
import ru.kf.KarlFriedrichBackendMvp.menu_item_category.MenuItemCategoryRepository;
import ru.kf.KarlFriedrichBackendMvp.menu_item.MenuItemRepository;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class MenuItemRepositoryTest {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private MenuItemCategoryRepository categoryRepository;

    private Long category1Id;
    private Long category2Id;
    private Long category3Id;
    private Long category4Id;

    private static final String category1 = "category1";
    private static final String category2 = "category2";
    private static final String category3 = "category3";
    private static final String category4 = "category4";

    @BeforeEach
    void setUp() {
        category1Id = categoryRepository.save(new MenuItemCategory(null, category1)).getId();
        category2Id = categoryRepository.save(new MenuItemCategory(null, category2)).getId();
        category3Id = categoryRepository.save(new MenuItemCategory(null, category3)).getId();
        category4Id = categoryRepository.save(new MenuItemCategory(null, category4)).getId();
    }

    @Test
    void saveDeleteTest() {
        String name = "Meat";
        MenuItem item = MenuItem.builder()
                .name(name)
                .available(true)
                .category(categoryRepository.getOne(category1Id))
                .imageUrl("url")
                .previewImageUrl("url")
                .price(40000)
                .build();

        item = menuItemRepository.save(item);

        assertEquals(category1, item.getCategory().getName());

        assertEquals(name, item.getName());

        menuItemRepository.deleteById(item.getId());
        assertNull(menuItemRepository.findById(item.getId()).orElse(null));
    }

}
