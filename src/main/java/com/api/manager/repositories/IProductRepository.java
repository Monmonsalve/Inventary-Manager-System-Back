package com.api.manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.manager.models.ProductModel;

@Repository
public interface IProductRepository extends JpaRepository<ProductModel, Long> {

    
}
