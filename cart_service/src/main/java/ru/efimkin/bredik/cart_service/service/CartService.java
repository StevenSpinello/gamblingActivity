package ru.efimkin.bredik.cart_service.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.efimkin.bredik.authservice.service.AuthService;
import ru.efimkin.bredik.cart_service.client.AuthClient;
import ru.efimkin.bredik.cart_service.model.CartModel;
import ru.efimkin.bredik.cart_service.repository.CartRepository;

import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final AuthClient authClient;

    public CartService(CartRepository cartRepository, AuthClient authClient) {
        this.cartRepository = cartRepository;
        this.authClient = authClient;
    }

    public List<CartModel> getAllItems(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Transactional
    public Mono<CartModel> addItem(CartModel cartModel) {
        // Запрос информации о пользователе через AuthClient
        return authClient.getUserById(cartModel.getUserId())
                .flatMap(user -> {
                    // Проверка, что userId из cartModel соответствует найденному пользователю
                    if (user != null && user.getId().equals(cartModel.getUserId())) {
                        // Сохраняем корзину, не изменяя userId
                        return Mono.just(cartRepository.save(cartModel));
                    } else {
                        // Если пользователь не найден или userId не совпадает
                        return Mono.error(new RuntimeException("Invalid userId"));
                    }
                });
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