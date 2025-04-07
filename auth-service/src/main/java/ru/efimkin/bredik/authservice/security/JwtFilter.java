package ru.efimkin.bredik.authservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import org.springframework.web.server.WebFilter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter implements WebFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserServiceDetailsImpl userServiceDetailsImpl;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = getTokenFromRequest(exchange.getRequest());

        if (token != null && jwtTokenProvider.validateJwtToken(token)) {
            String email = jwtTokenProvider.getEmailFromToken(token);
            UserDetailsImpl userDetails = userServiceDetailsImpl.loadUserByUsername(email);

            Authentication auth = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            // Устанавливаем в SecurityContext
            return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
        }

        return chain.filter(exchange);
    }

    private String getTokenFromRequest(ServerHttpRequest request) {
        List<String> authHeaders = request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION);
        if (!authHeaders.isEmpty()) {
            String bearer = authHeaders.get(0);
            if (bearer.startsWith("Bearer ")) {
                return bearer.substring(7);
            }
        }
        return null;
    }
}