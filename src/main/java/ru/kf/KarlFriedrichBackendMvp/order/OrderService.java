package ru.kf.KarlFriedrichBackendMvp.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kf.KarlFriedrichBackendMvp.menu_item.MenuItem;
import ru.kf.KarlFriedrichBackendMvp.menu_item.MenuItemRepository;
import ru.kf.KarlFriedrichBackendMvp.order.enums.OrderStatus;
import ru.kf.KarlFriedrichBackendMvp.order.enums.PaymentType;
import ru.kf.KarlFriedrichBackendMvp.security.user.User;
import ru.kf.KarlFriedrichBackendMvp.security.user.UserRepository;
import ru.kf.KarlFriedrichBackendMvp.table.Table;
import ru.kf.KarlFriedrichBackendMvp.table.TableRepository;

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
