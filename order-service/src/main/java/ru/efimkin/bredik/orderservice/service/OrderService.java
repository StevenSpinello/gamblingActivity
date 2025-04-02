package ru.efimkin.bredik.orderservice.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.efimkin.bredik.orderservice.client.CartClient;
import ru.efimkin.bredik.orderservice.dto.CartItemDto;
import ru.efimkin.bredik.orderservice.enums.OrderStatus;
import ru.efimkin.bredik.orderservice.model.OrderModel;
import ru.efimkin.bredik.orderservice.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartClient cartClient;

    public OrderService(OrderRepository orderRepository, CartClient cartClient) {
        this.orderRepository = orderRepository;
        this.cartClient = cartClient;
    }

    @Transactional
    public List<OrderModel> createOrder(Long userId) {
        List<CartItemDto> cartItems = cartClient.getAllItems(userId);

        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }
        List<OrderModel> orders = cartItems.stream()
                .map(cartItem -> OrderModel.builder()
                        .userId(userId)
                        .productId(cartItem.getProductId())
                        .quantity(cartItem.getQuantity())
                        .orderDate(LocalDateTime.now())
                        .status(OrderStatus.PENDING)
                        .build())
                .collect(Collectors.toList());

        List<OrderModel> savedOrders = orderRepository.saveAll(orders);
        cartClient.deleteAllItems(userId);
        return savedOrders;
    }
}
