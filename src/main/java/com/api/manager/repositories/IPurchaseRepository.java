package com.api.manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.manager.models.PurchaseModel;

@Repository
public interface IPurchaseRepository extends JpaRepository<PurchaseModel, Long>{
    
}
