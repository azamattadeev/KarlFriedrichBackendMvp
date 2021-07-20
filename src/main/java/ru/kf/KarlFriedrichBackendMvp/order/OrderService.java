package ru.kf.KarlFriedrichBackendMvp.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kf.KarlFriedrichBackendMvp.menu_item.MenuItem;
import ru.kf.KarlFriedrichBackendMvp.menu_item.MenuItemRepository;
import ru.kf.KarlFriedrichBackendMvp.security.user.User;
import ru.kf.KarlFriedrichBackendMvp.security.user.UserRepository;
import ru.kf.KarlFriedrichBackendMvp.table.Table;
import ru.kf.KarlFriedrichBackendMvp.table.TableRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Order createOrderForUser(Long userId, Long tableId, List<Long> itemIds) {
        Order order = new Order();

        setTableToOrder(order, tableId);
        setItemsAndPriceToOrder(order, itemIds);

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new IllegalArgumentException("Invalid user id");

        order.setOrderDateTime(LocalDateTime.now());

        order = orderRepository.save(order);
        user.setOrder(order);
        return order;
    }

    private void setTableToOrder(Order order, Long tableId) {
        Table table = tableRepository.findById(tableId).orElse(null);
        if (table == null) {
            throw new IllegalArgumentException("Invalid table id");
        }
        order.setTable(table);
    }

    private void setItemsAndPriceToOrder(Order order, List<Long> itemIds) {
        Set<MenuItem> itemSet = menuItemRepository.findAllByIdIn(itemIds);

        if (itemSet.stream().anyMatch(it -> !it.isAccessibility())) {
            throw new IllegalArgumentException("Menu item is not available");
        }

        Map<Long, MenuItem> itemMap = itemSet.stream()
                .collect(Collectors.toMap(MenuItem::getId, item -> item));

        List<MenuItem> resultItems = new ArrayList<>(itemIds.size());
        int price = 0;

        for (Long itemId : itemIds) {
            MenuItem item = itemMap.get(itemId);
            if (item == null) {
                throw new IllegalArgumentException("Unknown menu item");
            }

            resultItems.add(item);
            price += item.getPrice();
        }

        order.setItemsList(resultItems);
        order.setPrice(price);
    }

}
