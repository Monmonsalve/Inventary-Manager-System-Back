package com.api.manager.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.manager.models.InventoryModel;
import com.api.manager.services.InventoryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    //Get all inventory
    @GetMapping
    public ArrayList<InventoryModel> getInventory(){
        return this.inventoryService.getInventory(); 
    }

    //Create a new inventory Product
    @PostMapping
    public InventoryModel saveInventory(@Valid @RequestBody InventoryModel inventory){
        if(inventory != null){
            return this.inventoryService.saveInventory(inventory);
        }else{
            return null;
        }
    }

    //Put a Inventory
    @PutMapping("/{id}")
    public InventoryModel updateInventory(@Valid @RequestBody InventoryModel request, @PathVariable("id")Long id){
        return this.inventoryService.updateInventory(request, id);
    }

    //Delete Product on inventary by Id
    @DeleteMapping("/{id}")
    public String deleteInventoryById(@PathVariable("id")Long id){
        boolean ok = this.inventoryService.deleteInvetoryById(id);
        if(ok){
            return "Inventory deleted with id: "+ id;
        }else{
            return "Could not delete Inventory with id: "+ id;
        }
    }

}
