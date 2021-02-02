package ru.kf.KarlFriedrichBackendStub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kf.KarlFriedrichBackendStub.dto.incoming.CreateOrderDto;
import ru.kf.KarlFriedrichBackendStub.dto.outgoing.InfoAboutCreatedOrderDto;
import ru.kf.KarlFriedrichBackendStub.entities.Order;
import ru.kf.KarlFriedrichBackendStub.entities.User;
import ru.kf.KarlFriedrichBackendStub.services.OrderService;

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
                    createOrderDto.getPaymentType(),
                    createOrderDto.getTableId(),
                    createOrderDto.getItemIds());

            return ResponseEntity.ok(new InfoAboutCreatedOrderDto(order.getId(), order.getPriceInRoubles()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

}
