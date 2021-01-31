package ru.kf.KarlFriedrichBackendStub.integration.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kf.KarlFriedrichBackendStub.entities.*;
import ru.kf.KarlFriedrichBackendStub.entities.enums.OrderStatus;
import ru.kf.KarlFriedrichBackendStub.repositories.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private MenuItemCategoryRepository categoryRepository;

    private Long tableId;

    private MenuItemCategory category;

    private MenuItem soup;
    private MenuItem wine;

    private Order order;

    @BeforeEach
    void setUp() {
        String qrCode1 = "userTest_d234f3f43w2fa3w3d3d";
        int tableNumber = 712032899;
        tableId = tableRepository.save(new Table(null, tableNumber, qrCode1)).getId();
        category = categoryRepository.save(new MenuItemCategory(null, "user_TestCategory_3dfd"));
        soup = menuItemRepository.save(new MenuItem(
                null,
                "Soup 1",
                category,
                "description",
                235,
                true,
                "image url",
                "preview url"
        ));
        wine = menuItemRepository.save(new MenuItem(
                null,
                "Wine 1",
                category,
                "description",
                225,
                true,
                "image url",
                "preview url"
        ));

        order = new Order(
                null,
                OrderStatus.PAYMENT_OFFLINE,
                tableRepository.getOne(tableId),
                new ArrayList<>(),
                LocalDateTime.now()
        );
        order.getItemsList().add(soup);
        order.getItemsList().add(wine);

        order = orderRepository.save(order);
    }

    @AfterEach
    void tearDown() {
        tableRepository.deleteById(tableId);
        categoryRepository.delete(category);
        menuItemRepository.delete(soup);
        menuItemRepository.delete(wine);
        orderRepository.delete(order);
    }

    @Test
    public void saveDeleteWithoutOrderTest() {
        String email = "userReposTest_23dfgt@mail.com";
        User user = new User(
                null,
                email,
                "name",
                "ROLE_USER",
                "RU",
                null,
                true
        );
        user = userRepository.save(user);

        assertNotNull(user.getId());
        assertEquals(email, userRepository.getOne(user.getId()).getEmail());

        userRepository.delete(user);
    }

    @Test
    public void saveDeleteWithOrderTest() {
        String email = "userReposTest_lf34fvgn@mail.com";
        User user = new User(
                null,
                email,
                "name",
                "ROLE_USER",
                "RU",
                null,
                true
        );
        user = userRepository.save(user);
        long id = user.getId();
        user = userRepository.findById(id).orElse(null);

        assertNotNull(user);

        user.setOrder(order);
        userRepository.save(user);

        user = userRepository.findById(id).orElse(null);

        assertNotNull(user);
        assertEquals(order.getId(), user.getOrder().getId());
        assertEquals(order.getItemsList(), user.getOrder().getItemsList());
    }

}
