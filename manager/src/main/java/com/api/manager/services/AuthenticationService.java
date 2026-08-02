package com.api.manager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.manager.JwtService;
import com.api.manager.dto.LoginResponse;
import com.api.manager.dto.LoginRequest;
import com.api.manager.models.UserModel;
import com.api.manager.repositories.IUserRepository;

@Service
public class AuthenticationService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        UserModel user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        String token = jwtService.generateToken(user);
        return new LoginResponse(token);
}

}
