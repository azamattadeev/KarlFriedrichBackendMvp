package ru.kf.KarlFriedrichBackendMvp.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kf.KarlFriedrichBackendMvp.order.dto.CreateOrderDto;
import ru.kf.KarlFriedrichBackendMvp.order.dto.InfoAboutCreatedOrderDto;
import ru.kf.KarlFriedrichBackendMvp.security.user.User;

import javax.validation.Valid;

@RestController
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public ResponseEntity<InfoAboutCreatedOrderDto> createOrderForCurrentUser(@Valid @RequestBody CreateOrderDto createOrderDto) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            Order order = orderService.createOrderForUser(
                    currentUser.getId(),
                    createOrderDto.getTableId(),
                    createOrderDto.getItemIds());

            return ResponseEntity.ok(new InfoAboutCreatedOrderDto(order.getId(), order.getPrice()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

}
