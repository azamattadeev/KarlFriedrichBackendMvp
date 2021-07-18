package ru.kf.KarlFriedrichBackendMvp.integration.repositories;

import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    void tearDown() {
        categoryRepository.deleteById(category1Id);
        categoryRepository.deleteById(category2Id);
        categoryRepository.deleteById(category3Id);
        categoryRepository.deleteById(category4Id);
    }

    @Test
    void saveDeleteTest() {
        String name = "Meat";
        MenuItem item = MenuItem.builder()
                .name(name)
                .accessibility(true)
                .category(categoryRepository.getOne(category1Id))
                .imageUrl("url")
                .previewImageUrl("url")
                .priceInRoubles(400)
                .build();

        item = menuItemRepository.save(item);

        assertEquals(category1, item.getCategory().getName());

        assertEquals(name, item.getName());

        menuItemRepository.deleteById(item.getId());
        assertNull(menuItemRepository.findById(item.getId()).orElse(null));
    }

    @Test
    void findAllByAccessibilityIsTrue() {
        MenuItem item1 = MenuItem.builder()
                .name("Food 1")
                .accessibility(true)
                .category(categoryRepository.getOne(category1Id))
                .imageUrl("url")
                .previewImageUrl("url")
                .priceInRoubles(400)
                .build();

        MenuItem item2 = MenuItem.builder()
                .name("Food 2")
                .accessibility(true)
                .category(categoryRepository.getOne(category2Id))
                .imageUrl("url")
                .previewImageUrl("url")
                .priceInRoubles(300)
                .build();

        MenuItem item3 = MenuItem.builder()
                .name("Food 3")
                .accessibility(false)
                .category(categoryRepository.getOne(category1Id))
                .imageUrl("url")
                .previewImageUrl("url")
                .priceInRoubles(300)
                .build();

        item1 = menuItemRepository.save(item1);
        item2 = menuItemRepository.save(item2);
        item3 = menuItemRepository.save(item3);

        List<MenuItem> availableFood = menuItemRepository.findAllByAccessibilityIsTrue();

        assertEquals(2, availableFood.size());

        boolean contains1 = false;
        boolean contains2 = false;
        boolean contains3 = false;

        for (MenuItem menuItem : availableFood) {
            if (menuItem.getId().equals(item1.getId())) contains1 = true;
            if (menuItem.getId().equals(item2.getId())) contains2 = true;
            if (menuItem.getId().equals(item3.getId())) contains3 = true;
        }

        assertTrue(contains1);
        assertTrue(contains2);
        assertFalse(contains3);
    }

}
