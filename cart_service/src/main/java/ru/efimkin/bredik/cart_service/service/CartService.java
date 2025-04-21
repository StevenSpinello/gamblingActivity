package ru.efimkin.bredik.cart_service.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.efimkin.bredik.cart_service.client.AuthClient;
import ru.efimkin.bredik.cart_service.model.CartModel;
import ru.efimkin.bredik.cart_service.repository.CartRepository;

import java.util.List;

@Service
public class CartService {
    private final TransactionTemplate transactionTemplate;
    private final CartRepository cartRepository;
    private final AuthClient authClient;

    public CartService(CartRepository cartRepository, AuthClient authClient, PlatformTransactionManager transactionManager) {
        this.cartRepository = cartRepository;
        this.authClient = authClient;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    public List<CartModel> getAllItems(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public Mono<CartModel> addItem(CartModel cartModel) {
        return authClient.getUserById(cartModel.getUserId())
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
                .flatMap(user -> {
                    if (!user.getId().equals(cartModel.getUserId())) {
                        return Mono.error(new RuntimeException("Invalid userId"));
                    }

                    return Mono.fromCallable(() ->
                            transactionTemplate.execute(status ->
                                    cartRepository.save(cartModel)
                            )
                    ).subscribeOn(Schedulers.boundedElastic());
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