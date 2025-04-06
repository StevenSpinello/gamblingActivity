package ru.efimkin.bredik.orderservice.model;

import jakarta.persistence.*;
import lombok.*;
import ru.efimkin.bredik.orderservice.enums.OrderStatus;

import java.time.LocalDateTime;

@Entity
@Table (name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long productId;

    private int quantity;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


}
