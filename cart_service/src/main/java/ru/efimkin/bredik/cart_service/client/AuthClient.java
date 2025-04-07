package ru.efimkin.bredik.cart_service.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.efimkin.bredik.authservice.dto.UserDto;

@Component
public class AuthClient {
    private final WebClient webClient;

    public AuthClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://auth-service").build();
    }

    public Mono<UserDto> getUserById(Long id) {
        return webClient.get()
                .uri("/auth/{id}", id)
                .retrieve()
                .bodyToMono(UserDto.class);
    }

    public Mono<UserDto> getUserByEmail(String email) {
        return webClient.get()
                .uri("/auth/email/{email}", email)
                .retrieve()
                .bodyToMono(UserDto.class);
    }
}