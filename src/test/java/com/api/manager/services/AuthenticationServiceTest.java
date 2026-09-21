package com.api.manager.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.api.manager.JwtService;
import com.api.manager.dto.LoginRequest;
import com.api.manager.models.UserModel;
import com.api.manager.repositories.IUserRepository;

class AuthenticationServiceTest {

    @Test
    void loginReturnsTokenForValidCredentials() {
        IUserRepository repository = mock(IUserRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        JwtService jwtService = mock(JwtService.class);
        UserModel user = new UserModel();
        user.setEmail("isaac@example.com");
        user.setPassword("hash");
        when(repository.findByEmail("isaac@example.com")).thenReturn(Optional.of(user));
        when(encoder.matches("password", "hash")).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("jwt-token");

        AuthenticationService service = new AuthenticationService(repository, encoder, jwtService);
        LoginRequest request = new LoginRequest();
        request.setEmail("isaac@example.com");
        request.setPassword("password");

        assertThat(service.login(request).getToken()).isEqualTo("jwt-token");
    }

    @Test
    void loginRejectsInvalidPassword() {
        IUserRepository repository = mock(IUserRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        JwtService jwtService = mock(JwtService.class);
        UserModel user = new UserModel();
        user.setEmail("isaac@example.com");
        user.setPassword("hash");
        when(repository.findByEmail("isaac@example.com")).thenReturn(Optional.of(user));
        when(encoder.matches("wrong", "hash")).thenReturn(false);

        AuthenticationService service = new AuthenticationService(repository, encoder, jwtService);
        LoginRequest request = new LoginRequest();
        request.setEmail("isaac@example.com");
        request.setPassword("wrong");

        assertThrows(BadCredentialsException.class, () -> service.login(request));
    }
}
