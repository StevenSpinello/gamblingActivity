package ru.efimkin.bredik.cart_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.efimkin.bredik.cart_service.model.CartModel;
import ru.efimkin.bredik.cart_service.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity <List<CartModel>> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getAllItems(userId));
    }

    @PostMapping
    public Mono<ResponseEntity<CartModel>> addCart(@RequestBody CartModel cart) {

        return cartService.addItem(cart)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartModel> updateCart(@PathVariable Long id, @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.updateItemsQuantity(id, quantity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <Void> deleteItem(@PathVariable Long id) {
        cartService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity <Void> clearCart(@PathVariable Long userId) {
        cartService.deleteAllItems(userId);
        return ResponseEntity.noContent().build();
    }
}

