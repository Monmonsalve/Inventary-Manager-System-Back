package com.api.manager.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.manager.models.PurchaseModel;
import com.api.manager.repositories.IPurchaseRepository;

@Service
public class PurchaseService {
    
    @Autowired
    IPurchaseRepository purchaseRepository;

    public ArrayList<PurchaseModel> getPurchase(){
        return (ArrayList<PurchaseModel>) purchaseRepository.findAll();
    }

    public PurchaseModel savePurchase(PurchaseModel purchase){
        return purchaseRepository.save(purchase);
    }

    public Optional<PurchaseModel> getPurchaseById(Long id){
        return purchaseRepository.findById(id);
    }

    public PurchaseModel updatePurchase(PurchaseModel request, Long id){
        PurchaseModel purchase = purchaseRepository.findById(id).get();

        purchase.setStore(request.getStore());
        purchase.setSupplier(request.getSupplier());
        purchase.setUser(request.getUser());
        purchase.setPurchaseDetails(request.getPurchaseDetails());
        purchase.setTotalValue(request.getTotalValue());
        purchase.setDate(request.getDate());

        return purchase;
    }

    public Boolean deletePurchase(Long id){
        try{
            purchaseRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }

}
