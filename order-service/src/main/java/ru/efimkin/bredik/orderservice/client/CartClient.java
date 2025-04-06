package ru.efimkin.bredik.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import ru.efimkin.bredik.orderservice.dto.CartItemDto;

import java.util.List;

@FeignClient(name = "cart-service")
public interface CartClient {
    @GetMapping("/cart/{userId}")
    List<CartItemDto> getAllItems(@PathVariable Long userId);

    @DeleteMapping("/cart/clear/{userId}")
    void deleteAllItems(@PathVariable Long userId);
}
