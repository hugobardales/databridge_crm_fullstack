package com.databridge_crm_backend.service.auth;

import com.databridge_crm_backend.domain.model.Role;
import com.databridge_crm_backend.domain.model.User;
import com.databridge_crm_backend.domain.repository.UserRepository;
import com.databridge_crm_backend.dto.auth.RegisterRequest;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) { // call db to verify if
            throw new IllegalArgumentException("Email Already Use");
        }

        User user = new User(
                request.email(),
                passwordEncoder.encode(request.password()),
                Role.USER);
        userRepository.save(user);
    }
}
