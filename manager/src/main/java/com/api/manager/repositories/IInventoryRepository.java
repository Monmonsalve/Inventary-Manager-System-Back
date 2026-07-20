package com.api.manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.manager.models.InventoryModel;

@Repository
public interface IInventoryRepository extends JpaRepository<InventoryModel, Long> {

}
