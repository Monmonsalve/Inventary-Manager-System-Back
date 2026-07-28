package com.api.manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.manager.models.SaleModel;

@Repository
public interface ISaleRepository extends JpaRepository<SaleModel, Long>{
    
}
