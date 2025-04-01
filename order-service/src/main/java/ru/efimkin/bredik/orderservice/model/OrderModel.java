package ru.efimkin.bredik.orderservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table (name = "order")
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


}
