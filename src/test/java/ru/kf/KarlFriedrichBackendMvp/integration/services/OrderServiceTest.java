package ru.kf.KarlFriedrichBackendMvp.integration.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.kf.KarlFriedrichBackendMvp.menu_item.MenuItem;
import ru.kf.KarlFriedrichBackendMvp.menu_item.MenuItemRepository;
import ru.kf.KarlFriedrichBackendMvp.menu_item_category.MenuItemCategory;
import ru.kf.KarlFriedrichBackendMvp.menu_item_category.MenuItemCategoryRepository;
import ru.kf.KarlFriedrichBackendMvp.order.Order;
import ru.kf.KarlFriedrichBackendMvp.order.OrderService;
import ru.kf.KarlFriedrichBackendMvp.security.user.User;
import ru.kf.KarlFriedrichBackendMvp.security.user.UserRepository;
import ru.kf.KarlFriedrichBackendMvp.table.Table;
import ru.kf.KarlFriedrichBackendMvp.table.TableRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TableRepository tableRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private MenuItemCategoryRepository categoryRepository;

    private User user;
    private Table table;
    private MenuItemCategory category;
    private MenuItem item1;
    private MenuItem item2;

    @BeforeEach
    public void setUp() {
        user = new User(
                null,
                "order_service_test_3f43f@mail.com",
                "Name",
                "ROLE_USER",
                "RU",
                null,
                true);

        user = userRepository.save(user);

        table = new Table(null, 31922598, "OrderServiceTest_d34ud4cfc");
        table = tableRepository.save(table);

        category = categoryRepository.save(new MenuItemCategory(null, "OrderServiceTest_category_4d35g"));

        item1 = new MenuItem(
                null,
                "Item 1",
                category,
                "da_sfr44f",
                223,
                true,
                "url",
                "url"
        );
        item2 = new MenuItem(
                null,
                "Item 2",
                category,
                "mfr44f",
                233,
                true,
                "url",
                "url"
        );
        item1 = menuItemRepository.save(item1);
        item2 = menuItemRepository.save(item2);
    }

    @AfterEach
    public void tearDown() {
        userRepository.delete(user);
        tableRepository.delete(table);
        menuItemRepository.delete(item1);
        menuItemRepository.delete(item2);
        categoryRepository.delete(category);
    }

    @Test
    public void correctArgumentsTest() {
        List<Long> itemIds = new ArrayList<>(Arrays.asList(
                item1.getId(),
                item1.getId(),
                item1.getId(),
                item2.getId(),
                item2.getId()
        ));

        Order order = orderService.createOrderForUser(user.getId(), table.getId(), itemIds);

        assertNotNull(order);
        assertEquals(order, user.getOrder());
        assertEquals(5, user.getOrder().getItemsList().size());

        int item1Count = 0;
        int item2Count = 0;

        for (MenuItem item : user.getOrder().getItemsList()) {
            if (item.equals(item1)) item1Count++;
            if (item.equals(item2)) item2Count++;
        }
        assertEquals(3, item1Count);
        assertEquals(2, item2Count);
    }

}
