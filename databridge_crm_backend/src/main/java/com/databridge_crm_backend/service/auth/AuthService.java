package com.databridge_crm_backend.service.auth;

import com.databridge_crm_backend.domain.model.Role;
import com.databridge_crm_backend.domain.model.User;
import com.databridge_crm_backend.domain.repository.UserRepository;
import com.databridge_crm_backend.dto.auth.AuthResponse;
import com.databridge_crm_backend.dto.auth.LoginRequest;
import com.databridge_crm_backend.dto.auth.RegisterRequest;
import com.databridge_crm_backend.security.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static org.springframework.security.core.userdetails.User.withUsername;
import com.databridge_crm_backend.dto.auth.AuthResponse;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) { // call db to verify if
            throw new IllegalArgumentException("Email Already Use");
        }

        User user = new User(
                request.email(),
                passwordEncoder.encode(request.password()),
                Role.USER); // habilitado solo para registros como user
        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()));

        User user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        var userDetails = withUsername(user.getEmail())
                .password(user.getPasswordHash())
                .roles(user.getRole().name())
                .build();

        String jwt = jwtUtils.generateToken(userDetails);
        return new AuthResponse(jwt);

    }
}
