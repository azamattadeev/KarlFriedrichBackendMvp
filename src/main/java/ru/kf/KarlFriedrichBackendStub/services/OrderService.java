package ru.kf.KarlFriedrichBackendStub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kf.KarlFriedrichBackendStub.entities.MenuItem;
import ru.kf.KarlFriedrichBackendStub.entities.Order;
import ru.kf.KarlFriedrichBackendStub.entities.Table;
import ru.kf.KarlFriedrichBackendStub.entities.User;
import ru.kf.KarlFriedrichBackendStub.entities.enums.OrderStatus;
import ru.kf.KarlFriedrichBackendStub.entities.enums.PaymentType;
import ru.kf.KarlFriedrichBackendStub.repositories.MenuItemRepository;
import ru.kf.KarlFriedrichBackendStub.repositories.OrderRepository;
import ru.kf.KarlFriedrichBackendStub.repositories.TableRepository;
import ru.kf.KarlFriedrichBackendStub.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final TableRepository tableRepository;
    private final MenuItemRepository menuItemRepository;

    @Autowired
    public OrderService(UserRepository userRepository, OrderRepository orderRepository, TableRepository tableRepository, MenuItemRepository menuItemRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.tableRepository = tableRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public Order createOrderForUser(Long userId, PaymentType paymentType, Long tableId, List<Long> itemIds) {
        Order order = new Order();
        order.setStatus((paymentType == PaymentType.OFFLINE)
                ? OrderStatus.PAYMENT_OFFLINE
                : OrderStatus.PAYMENT_ONLINE);

        Table table = tableRepository.findById(tableId).orElse(null);
        if (table == null) {
            throw new IllegalArgumentException("Invalid table id");
        } else {
            order.setTable(table);
        }

        int priceInRoubles = 0;
        order.setItemsList(new ArrayList<>());
        for (Long itemId : itemIds) {
            MenuItem menuItem = menuItemRepository.findById(itemId).orElse(null);
            if (menuItem == null) throw new IllegalArgumentException("Invalid menu item id");
            if (!menuItem.isAccessibility()) throw new IllegalArgumentException("Menu item is not available");
            order.getItemsList().add(menuItem);
            priceInRoubles += menuItem.getPriceInRoubles();
        }
        order.setPriceInRoubles(priceInRoubles);

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new IllegalArgumentException("Invalid user id");

        order.setOrderDateTime(LocalDateTime.now());

        order = orderRepository.save(order);
        user.setOrder(order);
        userRepository.save(user);
        return order;
    }

}
