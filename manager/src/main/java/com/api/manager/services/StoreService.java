package com.api.manager.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.manager.models.StoreModel;
import com.api.manager.repositories.IStoreRepository;

@Service
public class StoreService {
    
    @Autowired
    private IStoreRepository storeRepository;

    //Get all store
    public ArrayList<StoreModel> getStore(){
        return (ArrayList<StoreModel>) storeRepository.findAll();
    }

    //Create a new Store
    public StoreModel saveStore(StoreModel store){
        return storeRepository.save(store);
    }

    //Find store by Id
    public Optional<StoreModel> getStoreById(Long id){
        return storeRepository.findById(id);
    }

    //Update store
    public StoreModel updateStoreById(StoreModel request, Long id){
        StoreModel store = storeRepository.findById(id).get();

        store.setName(request.getName());
        store.setEmail(request.getEmail());
        store.setAddress(request.getAddress());
        store.setPhoneNumber(request.getPhoneNumber());

        return store;
    }

    //Delete store By Id
    public Boolean deleteStore(Long id){
        try{
            storeRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }

}
