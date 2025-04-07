package ru.efimkin.bredik.authservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.efimkin.bredik.authservice.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceDetailsImpl {

    private final UserRepository userRepository;

    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

}