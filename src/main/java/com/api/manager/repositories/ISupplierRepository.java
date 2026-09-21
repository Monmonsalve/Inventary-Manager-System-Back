package com.api.manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.manager.models.SupplierModel;

@Repository
public interface ISupplierRepository extends JpaRepository<SupplierModel, Long>{
    
}
