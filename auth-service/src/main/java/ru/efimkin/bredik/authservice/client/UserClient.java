package ru.efimkin.bredik.authservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.efimkin.bredik.authservice.dto.UserDto;

@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/users/username/{username}")
    UserDto getUserByUsername(@RequestParam String username);

    @GetMapping("/users/email/{email}")
    UserDto getUserByEmail(@RequestParam String email);
}
