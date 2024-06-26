package ru.kf.KarlFriedrichBackendMvp.repositories;

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
import ru.kf.KarlFriedrichBackendMvp.order.OrderRepository;
import ru.kf.KarlFriedrichBackendMvp.table.Table;
import ru.kf.KarlFriedrichBackendMvp.table.TableRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private MenuItemCategoryRepository categoryRepository;

    private Long table1Id;
    private Long table2Id;

    private MenuItemCategory category;

    private MenuItem soup;
    private MenuItem meat;
    private MenuItem juice;
    private MenuItem wine;

    @BeforeEach
    void setUp() {
        String qrCode1 = "orderTest_d234f3f43w2fa3w3d3d";
        int tableNumber1 = 312094819;
        table1Id = tableRepository.save(new Table(null, tableNumber1, qrCode1, 10003242)).getId();
        String qrCode2 = "orderTest_f43w43d3fdf32iec1ds";
        int tableNumber2 = 408329124;
        table2Id = tableRepository.save(new Table(null, tableNumber2, qrCode2, 10004124)).getId();
        category = categoryRepository.save(new MenuItemCategory(null, "order_TestCategory_3d2d"));
        soup = menuItemRepository.save(new MenuItem(
                null,
                "Soup 1",
                category,
                "description",
                235,
                true,
                "image url",
                "preview url",
                3324114
        ));
        meat = menuItemRepository.save(new MenuItem(
                null,
                "Meat 1",
                category,
                "description",
                225,
                true,
                "image url",
                "preview url",
                3145322
        ));
        juice = menuItemRepository.save(new MenuItem(
                null,
                "Juice 1",
                category,
                "description",
                225,
                true,
                "image url",
                "preview url",
                3124153
        ));
        wine = menuItemRepository.save(new MenuItem(
                null,
                "Wine 1",
                category,
                "description",
                225,
                true,
                "image url",
                "preview url",
                412415324
        ));
    }

    @Test
    void saveDeleteTest() {
        Order order = new Order(
                null,
                tableRepository.getOne(table1Id),
                100,
                new ArrayList<>(),
                LocalDateTime.now()
        );
        order.getItemsList().add(soup);
        order.getItemsList().add(soup);
        order.getItemsList().add(meat);
        order.getItemsList().add(juice);
        order.getItemsList().add(wine);
        order.getItemsList().add(wine);

        order = orderRepository.save(order);

        assertNotNull(order.getId());
        assertEquals(6, order.getItemsList().size());
        assertEquals(6, orderRepository.getOne(order.getId()).getItemsList().size());

        int countMeat = 0;
        int countWine = 0;
        for (MenuItem menuItem : order.getItemsList()) {
            if (menuItem.equals(meat)) countMeat++;
            if (menuItem.equals(wine)) countWine++;
        }

        assertEquals(1, countMeat);
        assertEquals(2, countWine);

        orderRepository.delete(order);
    }

}
