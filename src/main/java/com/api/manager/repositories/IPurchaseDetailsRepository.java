package com.api.manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.manager.models.PurchaseDetailsModel;

@Repository
public interface IPurchaseDetailsRepository extends JpaRepository<PurchaseDetailsModel, Long> {
    
}
