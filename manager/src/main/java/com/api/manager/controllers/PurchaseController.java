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

import com.api.manager.models.PurchaseModel;
import com.api.manager.services.PurchaseService;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    
    @Autowired
    private PurchaseService purchaseService;

    @GetMapping
    public ArrayList<PurchaseModel> getPurchase(){
        return (ArrayList<PurchaseModel>) purchaseService.getPurchase();
    }

    @PostMapping
    public PurchaseModel savePurchase(@RequestBody PurchaseModel purchase){
        if(purchase != null){
        return this.purchaseService.savePurchase(purchase);
        }else{
            return null;
        }
        }

    @GetMapping("/{id}")
    public Optional<PurchaseModel> getPurchaseById(@PathVariable("id") Long id){
        return purchaseService.getPurchaseById(id);
    }

    @PutMapping("/{id}")
    public PurchaseModel updatePurchase(@RequestBody PurchaseModel request, Long id){
        return this.purchaseService.updatePurchase(request, id);
    }

    @DeleteMapping("/{id}")
    public String deletePurchase(Long id){
        boolean ok = this.purchaseService.deletePurchase(id);
        if(ok){
            return "Purchase deleted with id: " + id;
        }else{
            return "Could not deleted Purchase with id: " + id;
        }
    }

}