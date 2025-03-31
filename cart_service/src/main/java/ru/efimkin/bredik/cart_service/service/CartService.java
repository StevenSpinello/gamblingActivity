package ru.efimkin.bredik.cart_service.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import ru.efimkin.bredik.cart_service.model.CartModel;
import ru.efimkin.bredik.cart_service.repository.CartRepository;

import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<CartModel> getAllItems(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Transactional
    public CartModel addItem(CartModel cartModel) {
        return cartRepository.save(cartModel);
    }

    public CartModel updateItemsQuantity(Long id, int quantity ) {
        CartModel cartModel = cartRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Failed to find cart with id: " + id));
        cartModel.setQuantity(quantity);
        return cartRepository.save(cartModel);
    }

    public void deleteItem(Long id) {
        cartRepository.deleteById(id);
    }

    public void deleteAllItems(Long userId) {
        List<CartModel> allItems = cartRepository.findByUserId(userId);
        cartRepository.deleteAll(allItems);
    }
}
