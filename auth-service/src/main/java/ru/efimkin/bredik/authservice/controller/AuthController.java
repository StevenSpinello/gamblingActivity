package ru.efimkin.bredik.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.efimkin.bredik.authservice.dto.JwtAuthDto;
import ru.efimkin.bredik.authservice.dto.RefreshToken;
import javax.naming.AuthenticationException;
import ru.efimkin.bredik.authservice.dto.UserCredentialsDto;
import ru.efimkin.bredik.authservice.dto.UserDto;
import ru.efimkin.bredik.authservice.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sing-in")
    public ResponseEntity<JwtAuthDto> singIn(@RequestBody UserCredentialsDto userCredentialsDto) {
        try {
            JwtAuthDto jwtAuthenticationDto = authService.singIn(userCredentialsDto);
            return ResponseEntity.ok(jwtAuthenticationDto);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/refresh")
    public JwtAuthDto refresh(@RequestBody RefreshToken refreshToken) throws Exception {
        return authService.refreshToken(refreshToken);
    }

    @PostMapping("/registration")
    public String createUser(@RequestBody UserDto userDto) {
        System.out.println("Контроллер вызван");
        return authService.addUser(userDto);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        return authService.getUserById(id);
    }

    @GetMapping("/email/{email}")
    public UserDto getUserByEmail(@PathVariable String email) throws ChangeSetPersister.NotFoundException {
        return authService.getUserByEmail(email);
    }

}