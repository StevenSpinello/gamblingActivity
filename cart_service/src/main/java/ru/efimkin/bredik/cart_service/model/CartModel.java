package ru.efimkin.bredik.cart_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long productId;

    @Min(1)
    private int quantity;


}
