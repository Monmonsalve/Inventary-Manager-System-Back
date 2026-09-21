package com.api.manager.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.api.manager.dto.CreateUserRequest;
import com.api.manager.dto.UserResponse;
import com.api.manager.exception.BusinessException;
import com.api.manager.models.RoleModel;
import com.api.manager.models.UserModel;
import com.api.manager.repositories.IRoleRepository;
import com.api.manager.repositories.IUserRepository;

class UserServiceTest {

    private IUserRepository userRepository;
    private IRoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(IUserRepository.class);
        roleRepository = mock(IRoleRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserService(userRepository, roleRepository, passwordEncoder);
    }

    @Test
    void registerAssignsUserRoleAndHashesPassword() {
        RoleModel role = new RoleModel();
        role.setName("USER");
        when(userRepository.existsByEmail("isaac@example.com")).thenReturn(false);
        when(roleRepository.findByNameIgnoreCase("USER")).thenReturn(Optional.of(role));
        when(passwordEncoder.encode("strong-password")).thenReturn("bcrypt-hash");
        when(userRepository.save(any(UserModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserResponse response = userService.register(
                new CreateUserRequest("Isaac", "Monsalve", "ISAAC@example.com", "strong-password"));

        assertThat(response.email()).isEqualTo("isaac@example.com");
        assertThat(response.role()).isEqualTo("USER");
        verify(passwordEncoder).encode("strong-password");
    }

    @Test
    void registerRejectsAnExistingEmail() {
        when(userRepository.existsByEmail("isaac@example.com")).thenReturn(true);

        assertThrows(BusinessException.class, () -> userService.register(
                new CreateUserRequest("Isaac", "Monsalve", "isaac@example.com", "strong-password")));
    }
}
