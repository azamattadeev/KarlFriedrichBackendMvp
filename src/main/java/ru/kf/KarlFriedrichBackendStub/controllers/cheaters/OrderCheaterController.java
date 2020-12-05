package ru.kf.KarlFriedrichBackendStub.controllers.cheaters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kf.KarlFriedrichBackendStub.entities.Order;
import ru.kf.KarlFriedrichBackendStub.repositories.OrderRepository;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cheaters/order/")
public class OrderCheaterController {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderCheaterController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> ordersList = orderRepository.findAll();
        return ResponseEntity.ok(ordersList);
    }

    @GetMapping("{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        return (order != null)
                ? ResponseEntity.ok(order)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> addOrder(@RequestBody Order order) {
        order = orderRepository.save(order);
        return ResponseEntity.created(URI.create("/cheaters/table/" + order.getId())).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        order.setId(id);
        orderRepository.save(order);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Long id) {
        orderRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
