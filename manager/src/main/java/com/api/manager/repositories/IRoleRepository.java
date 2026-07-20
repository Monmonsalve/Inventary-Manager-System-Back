package com.api.manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.manager.models.RoleModel;

@Repository
public interface IRoleRepository extends JpaRepository<RoleModel, Long>{

    
} 
