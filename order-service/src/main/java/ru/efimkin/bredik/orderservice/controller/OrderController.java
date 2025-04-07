package ru.efimkin.bredik.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.efimkin.bredik.orderservice.model.OrderModel;
import ru.efimkin.bredik.orderservice.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/checkout/{userId}")
    public Mono<ResponseEntity<List<OrderModel>>> createOrder(@PathVariable Long userId) {
        return orderService.createOrder(userId)
                .map(ResponseEntity::ok);
    }
}