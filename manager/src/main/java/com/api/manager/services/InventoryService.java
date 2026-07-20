package com.api.manager.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.manager.models.InventoryModel;
import com.api.manager.repositories.IInventoryRepository;

@Service
public class InventoryService {
    
    @Autowired
    IInventoryRepository inventoryRepository;

    //Get all Inventory (For the moment no By Id store)
    public ArrayList<InventoryModel> getInventory(){
        return (ArrayList<InventoryModel>) inventoryRepository.findAll();
    }

    //Create a Product in Inventory
    public InventoryModel saveInventory(InventoryModel inventory){
        return inventoryRepository.save(inventory);
    }

    //Update a product in Inventory
    public InventoryModel updateInventory(InventoryModel request, Long id){
        InventoryModel inventory = inventoryRepository.findById(id).get();

        inventory.setProduct(request.getProduct());
        inventory.setQuantity(request.getQuantity());
        inventory.setStore(request.getStore());

        return inventoryRepository.save(inventory);
    }
    
    //Delete a product in Inventory
    public boolean deleteInvetoryById(Long id){
        try{
            inventoryRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }

}
