package ru.efimkin.bredik.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.efimkin.bredik.orderservice.dto.CartItemDto;

import java.util.List;

@Component
public class CartClient {
    private final WebClient webClient;

    public CartClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://cart-service").build();
    }

    public Flux<CartItemDto> getAllItems(Long userId) {
        return webClient.get()
                .uri("/cart/{userId}", userId)
                .retrieve()
                .bodyToFlux(CartItemDto.class);
    }

    public Mono<Void> deleteAllItems(Long userId) {
        return webClient.delete()
                .uri("/cart/clear/{userId}", userId)
                .retrieve()
                .bodyToMono(Void.class);
    }
}

