package ru.kf.KarlFriedrichBackendStub.unit.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kf.KarlFriedrichBackendStub.entities.MenuItemCategory;
import ru.kf.KarlFriedrichBackendStub.repositories.MenuItemCategoryRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MenuItemCategoryRepositoryTest {

    @Autowired
    private MenuItemCategoryRepository categoryRepository;

    @Test
    void saveDeleteTest() {
        MenuItemCategory category = new MenuItemCategory(null, "Category 1");

        category = categoryRepository.save(category);

        assertNotNull(category.getId());

        assertEquals("Category 1", category.getName());

        categoryRepository.delete(category);
        assertNull(categoryRepository.findById(category.getId()).orElse(null));
    }

    @Test
    void findByNameTest() {
        MenuItemCategory category1 = new MenuItemCategory(null, "Category 1");
        MenuItemCategory category2 = new MenuItemCategory(null, "Category 2");
        MenuItemCategory category3 = new MenuItemCategory(null, "Category 3");

        category1 = categoryRepository.save(category1);
        category2 = categoryRepository.save(category2);
        category3 = categoryRepository.save(category3);

        assertEquals(category2.getId(), categoryRepository.findMenuItemCategoryByName("Category 2").getId());

        assertEquals(category1, categoryRepository.findMenuItemCategoryByName("Category 1"));

        assertNotEquals(category3, categoryRepository.findMenuItemCategoryByName("Category 2"));

        categoryRepository.delete(category1);
        categoryRepository.delete(category2);
        categoryRepository.delete(category3);
    }

}
