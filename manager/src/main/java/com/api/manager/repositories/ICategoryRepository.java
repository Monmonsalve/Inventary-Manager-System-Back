package com.api.manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.manager.models.CategoryModel;

@Repository
public interface ICategoryRepository  extends JpaRepository<CategoryModel, Long >{
    
}
