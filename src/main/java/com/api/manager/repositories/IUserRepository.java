package com.api.manager.repositories;

import org.springframework.stereotype.Repository;
import com.api.manager.models.UserModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface IUserRepository extends JpaRepository<UserModel, Long> {
    
    Optional<UserModel> findByEmail(String email);

    boolean existsByEmail(String email);

}
