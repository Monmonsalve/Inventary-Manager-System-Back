package com.api.manager.repositories;

import org.springframework.stereotype.Repository;
import com.api.manager.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface IUserRepository extends JpaRepository<UserModel, Long> {
    
}
