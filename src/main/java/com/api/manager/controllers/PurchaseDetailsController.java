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

import com.api.manager.models.PurchaseDetailsModel;
import com.api.manager.services.PurchaseDetailsService;

@RestController
@RequestMapping("/purchase/Details")
public class PurchaseDetailsController {
    
    @Autowired
    private PurchaseDetailsService purchaseDetailsService;

    //Route Get all Details Purchase
    @GetMapping
    public ArrayList<PurchaseDetailsModel> getAllDetails(){
        return this.purchaseDetailsService.getPurchaseDetails();
    }

    //Route Create a new Details Purchase
    @PostMapping
    public PurchaseDetailsModel saveDetails(@RequestBody PurchaseDetailsModel purchase){
        if(purchase != null){
            return this.purchaseDetailsService.saveDetailsPurchase(purchase);
        }else{
            return null;
        }
    }

    //Route Get Details Purchase By Id
    @GetMapping("/{id}")
    public Optional<PurchaseDetailsModel> getPurchaseDetailsById(@PathVariable("id") Long id){
        return this.purchaseDetailsService.getDetailsById(id);
    }

    //Route Update Details Purchase
    @PutMapping("/{id}")
    public PurchaseDetailsModel updateDetails(@RequestBody PurchaseDetailsModel request, @PathVariable("id") Long id){
        return this.purchaseDetailsService.updateDetails(request, id);
    }

    //Route Delete Details Purchase
    @DeleteMapping("/{id}")
    public String deleteDetails(@PathVariable("id") Long id){
        boolean ok = this.purchaseDetailsService.deleteDetailsPurchase(id);
        if(ok){
            return "Details Purchase deleted with id: " + id;
        }else{
            return "Could not deleted Details Purchase with id: " + id;
        }
    }

}
