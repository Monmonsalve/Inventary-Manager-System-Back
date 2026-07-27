package com.api.manager.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.manager.models.StoreModel;
import com.api.manager.services.StoreService;

@RestController
@RequestMapping("/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    //Get all store
    @GetMapping
    public ArrayList<StoreModel> getStore(){
        return this.storeService.getStore();
    }

    //Create a new Store
    @PostMapping
    public StoreModel saveStore(@RequestBody StoreModel store){
        if(store != null){
            return this.storeService.saveStore(store);
        }else{
            return null;
        }
    }

    //Get Store By Id
    @GetMapping(path="/{id}")
    public Optional<StoreModel> getStoreById(@PathVariable("id") Long id){
        return this.storeService.getStoreById(id);
    }

    //Update Store By Id
    @PutMapping(path="/{id}")
    public StoreModel updateStore(@RequestBody StoreModel store, @PathVariable("id") Long id){
        return this.storeService.updateStoreById(store, id);
    }

    //Delete Store
    @DeleteMapping(path = "/{id}")
    public String deleteStore(@PathVariable("id") Long id){
        boolean ok = this.storeService.deleteStore(id);
        if(ok){
            return "Store Deleted with id: " + id;
        }else{
            return "Could not delete store with id: " + id;
        }
    }

}
