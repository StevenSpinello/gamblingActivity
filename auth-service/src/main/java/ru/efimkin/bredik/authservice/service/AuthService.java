package ru.efimkin.bredik.authservice.service;


import org.springframework.data.crossstore.ChangeSetPersister;
import ru.efimkin.bredik.authservice.dto.JwtAuthDto;
import ru.efimkin.bredik.authservice.dto.RefreshToken;
import ru.efimkin.bredik.authservice.dto.UserCredentialsDto;
import ru.efimkin.bredik.authservice.dto.UserDto;
import javax.naming.AuthenticationException;

public interface AuthService {
    JwtAuthDto singIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException;
    JwtAuthDto refreshToken(RefreshToken refreshTokenDto) throws Exception;
    UserDto getUserById(Long id) throws ChangeSetPersister.NotFoundException;
    UserDto getUserByEmail(String email) throws ChangeSetPersister.NotFoundException;
    String addUser(UserDto user);
}