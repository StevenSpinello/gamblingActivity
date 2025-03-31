package ru.efimkin.bredik.cart_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.efimkin.bredik.cart_service.model.CartModel;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository <CartModel, Long> {
    List<CartModel> findByUserId(Long userId);
}
