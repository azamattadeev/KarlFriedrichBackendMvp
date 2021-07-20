package ru.kf.KarlFriedrichBackendMvp.functional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.kf.KarlFriedrichBackendMvp.order.Order;
import ru.kf.KarlFriedrichBackendMvp.order.OrderRepository;
import ru.kf.KarlFriedrichBackendMvp.security.confirmation.ConfirmationDto;
import ru.kf.KarlFriedrichBackendMvp.order.dto.CreateOrderDto;
import ru.kf.KarlFriedrichBackendMvp.security.signup.SignUpDto;
import ru.kf.KarlFriedrichBackendMvp.security.tokens.AccessRefreshTokenDto;
import ru.kf.KarlFriedrichBackendMvp.security.current_user.CurrentUserDto;
import ru.kf.KarlFriedrichBackendMvp.order.dto.InfoAboutCreatedOrderDto;
import ru.kf.KarlFriedrichBackendMvp.menu.MenuDto;
import ru.kf.KarlFriedrichBackendMvp.menu_item.MenuItem;
import ru.kf.KarlFriedrichBackendMvp.menu_item.MenuItemRepository;
import ru.kf.KarlFriedrichBackendMvp.menu_item_category.MenuItemCategory;
import ru.kf.KarlFriedrichBackendMvp.menu_item_category.MenuItemCategoryRepository;
import ru.kf.KarlFriedrichBackendMvp.security.user.User;
import ru.kf.KarlFriedrichBackendMvp.security.user.UserRepository;
import ru.kf.KarlFriedrichBackendMvp.table.Table;
import ru.kf.KarlFriedrichBackendMvp.table.TableRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CorrectScenarioTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MenuItemCategoryRepository categoryRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private TableRepository tableRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    private static final String QR_CODE = "CorrectScenarioTest_qrCode_f4d22hfb9";
    private static final int TABLE_NUMBER = 1931511942;

    private MenuItemCategory category;

    private MenuItem item1;
    private MenuItem item2;

    private Table table;

    @BeforeEach
    public void setUp() {
        category = categoryRepository.save(new MenuItemCategory(null, "Category"));
        item1 = menuItemRepository.save(new MenuItem(
                null,
                "Item 1",
                category,
                "Description",
                300,
                true,
                "url",
                "url"
        ));
        item2 = menuItemRepository.save(new MenuItem(
                null,
                "Item 2",
                category,
                "Description",
                530,
                true,
                "url",
                "url"
        ));
        table = tableRepository.save(new Table(null, TABLE_NUMBER, QR_CODE));
    }

    @AfterEach
    public void tearDown() {
        tableRepository.delete(table);
        menuItemRepository.delete(item1);
        menuItemRepository.delete(item2);
        categoryRepository.delete(category);
    }

    @Test
    public void correctScenarioTest() throws Exception {
        String email = "correct_scenario_test_d32email@mail.com";
        String name = "Scenario";
        String language = "RU";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConverter.mapToJson(new SignUpDto(
                        email, name, language
                )))
        ).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConverter.mapToJson(
                        new ConfirmationDto(email, 100500)
                ))
        ).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        AccessRefreshTokenDto accessRefresh = JsonConverter.mapFromJson(mvcResult.getResponse().getContentAsString(), AccessRefreshTokenDto.class);

        mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/menu")
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        MenuDto[] menuItemPreviews = JsonConverter.mapFromJson(
                mvcResult.getResponse().getContentAsString(), MenuDto[].class);

        assertEquals(2, menuItemPreviews.length);

        mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/table")
                .param("qrCode", QR_CODE)
                .header("AUTHORIZATION", accessRefresh.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        Table table = JsonConverter.mapFromJson(
                mvcResult.getResponse().getContentAsString(), Table.class);

        assertEquals(TABLE_NUMBER, table.getTableNumber());

        int price = 3 * menuItemPreviews[0].getPriceInRoubles() + menuItemPreviews[1].getPriceInRoubles();
        List<Long> itemIds = new ArrayList<>(Arrays.asList(
                menuItemPreviews[0].getId(),
                menuItemPreviews[0].getId(),
                menuItemPreviews[0].getId(),
                menuItemPreviews[1].getId()
        ));

        mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/order")
                .header("AUTHORIZATION", accessRefresh.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConverter.mapToJson(new CreateOrderDto(
                        table.getId(),
                        PaymentType.OFFLINE,
                        itemIds
                )))
        ).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        InfoAboutCreatedOrderDto infoAboutCreatedOrderDto = JsonConverter.mapFromJson(
                mvcResult.getResponse().getContentAsString(), InfoAboutCreatedOrderDto.class);

        assertEquals(price, infoAboutCreatedOrderDto.getPrice());

        Order order = orderRepository.findById(infoAboutCreatedOrderDto.getId()).orElse(null);
        assertNotNull(order);

        assertEquals(price, order.getPriceInRoubles());

        mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/current-user")
                .header("AUTHORIZATION", accessRefresh.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        CurrentUserDto currentUserDto = JsonConverter.mapFromJson(mvcResult.getResponse().getContentAsString(), CurrentUserDto.class);

        assertEquals(email, currentUserDto.getEmail());

        User user = userRepository.findById(currentUserDto.getId()).orElse(null);
        assertNotNull(user);

        user.setOrder(null);
        userRepository.save(user);
        orderRepository.delete(order);
        assertTrue(userRepository.existsById(currentUserDto.getId()));
        userRepository.deleteById(currentUserDto.getId());

        assertFalse(userRepository.existsById(currentUserDto.getId()));
        assertFalse(orderRepository.existsById(order.getId()));
    }

}
