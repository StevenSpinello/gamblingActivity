package ru.efimkin.bredik.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.efimkin.bredik.authservice.dto.JwtAuthDto;
import ru.efimkin.bredik.authservice.dto.RefreshToken;
import ru.efimkin.bredik.authservice.dto.UserCredentialsDto;
import ru.efimkin.bredik.authservice.dto.UserDto;
import ru.efimkin.bredik.authservice.mapper.UserMapper;
import ru.efimkin.bredik.authservice.model.Users;
import ru.efimkin.bredik.authservice.repository.UserRepository;
import ru.efimkin.bredik.authservice.security.JwtTokenProvider;
import javax.naming.AuthenticationException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtAuthDto singIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        Users user = findByCredentials(userCredentialsDto);
        return jwtTokenProvider.generateAuthToken(user.getEmail());
    }


    @Override
    public JwtAuthDto refreshToken(RefreshToken refreshTokenDto) throws Exception {
        String refreshToken = refreshTokenDto.getRefreshToken();
        if (refreshToken != null && jwtTokenProvider.validateJwtToken(refreshToken)) {
            Users user = findByEmail(jwtTokenProvider.getEmailFromToken(refreshToken));
            return jwtTokenProvider.refreshBaseToken(user.getEmail(), refreshToken);
        }
        throw new  AuthenticationException("Invalid refresh token");
    }

    @Override
    @Transactional
    public UserDto getUserById(Long id) throws ChangeSetPersister.NotFoundException {
        return userMapper.toDto(userRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new));
    }

    @Override
    @Transactional
    public UserDto getUserByEmail(String email) throws ChangeSetPersister.NotFoundException {
        return userMapper.toDto(userRepository.findByEmail(email)
                .orElseThrow(ChangeSetPersister.NotFoundException::new));
    }

    @Override
    public String addUser(UserDto userDto) {
        // Маппим UserDto в Users
        Users user = userMapper.toEntity(userDto);

        // Проверяем, если username null, то присваиваем его значению email
        if (user.getUsername() == null) {
            user.setUsername(user.getEmail());  // используем email как username
        }

        // Кодируем пароль
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Сохраняем пользователя в базу данных
        userRepository.save(user);

        return "User added";
    }

    private Users findByCredentials(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        Optional<Users> optionalUser = userRepository.findByEmail(userCredentialsDto.getEmail());
        if (optionalUser.isPresent()){
            Users user = optionalUser.get();
            if (passwordEncoder.matches(userCredentialsDto.getPassword(), user.getPassword())){
                return user;
            }
        }
        throw new AuthenticationException("Email or password is not correct");
    }

    private Users findByEmail(String email) throws Exception {
        return userRepository.findByEmail(email).orElseThrow(()->
                new Exception(String.format("User with email %s not found", email)));
    }
}