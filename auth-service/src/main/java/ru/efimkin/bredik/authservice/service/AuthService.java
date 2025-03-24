package ru.efimkin.bredik.authservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.efimkin.bredik.authservice.client.UserClient;
import ru.efimkin.bredik.authservice.dto.LoginRequest;
import ru.efimkin.bredik.authservice.dto.LoginResponse;
import ru.efimkin.bredik.authservice.dto.RegisterRequest;
import ru.efimkin.bredik.authservice.dto.UserDto;
import ru.efimkin.bredik.authservice.model.User;
import ru.efimkin.bredik.authservice.repository.UserRepository;
import ru.efimkin.bredik.authservice.security.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserClient userClient;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public String register(RegisterRequest request) {
        // Проверяем, существует ли пользователь в UserService
        try {
            userClient.getUserByUsername(request.getUsername());
            throw new RuntimeException("Username already exists!");
        } catch (Exception ignored) {}

        try {
            userClient.getUserByEmail(request.getEmail());
            throw new RuntimeException("Email already exists!");
        } catch (Exception ignored) {}

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        return "User registered successfully!";
    }


    public LoginResponse login(LoginRequest request) {
        // Получаем пользователя из UserService
        UserDto user = userClient.getUserByUsername(request.getUsername());

        if (user == null || !user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid username or password!");
        }

        // Генерируем JWT-токен
        String token = jwtTokenProvider.generateToken(request.getUsername());

        return new LoginResponse(token);
    }

}
