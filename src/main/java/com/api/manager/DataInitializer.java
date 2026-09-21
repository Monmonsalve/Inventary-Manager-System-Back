package com.api.manager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.api.manager.models.RoleModel;
import com.api.manager.models.UserModel;
import com.api.manager.repositories.IRoleRepository;
import com.api.manager.repositories.IUserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedRolesAndOptionalAdmin(IRoleRepository roleRepository, IUserRepository userRepository,
            PasswordEncoder passwordEncoder, Environment environment) {
        return args -> {
            createRoleIfMissing(roleRepository, "USER");
            RoleModel adminRole = createRoleIfMissing(roleRepository, "ADMIN");

            String adminEmail = environment.getProperty("app.bootstrap-admin.email", "").trim().toLowerCase();
            String adminPassword = environment.getProperty("app.bootstrap-admin.password", "");
            if (!adminEmail.isBlank() && !adminPassword.isBlank() && !userRepository.existsByEmail(adminEmail)) {
                UserModel admin = new UserModel();
                admin.setFirstName("System");
                admin.setLastName("Administrator");
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRole(adminRole);
                userRepository.save(admin);
            }
        };
    }

    private RoleModel createRoleIfMissing(IRoleRepository roleRepository, String name) {
        return roleRepository.findByNameIgnoreCase(name).orElseGet(() -> {
            RoleModel role = new RoleModel();
            role.setName(name);
            return roleRepository.save(role);
        });
    }
}
