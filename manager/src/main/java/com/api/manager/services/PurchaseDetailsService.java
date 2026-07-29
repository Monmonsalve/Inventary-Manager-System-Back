package com.api.manager.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.manager.models.PurchaseDetailsModel;
import com.api.manager.repositories.IPurchaseDetailsRepository;

@Service
public class PurchaseDetailsService {
    
    @Autowired
    IPurchaseDetailsRepository purchaseDetailsRepository;

    //Get all Details Purchase
    public ArrayList<PurchaseDetailsModel> getPurchaseDetails(){
        return (ArrayList<PurchaseDetailsModel>) purchaseDetailsRepository.findAll();
    }

    //Create a detials of purchase
    public PurchaseDetailsModel saveDetailsPurchase(PurchaseDetailsModel purchase){
        return purchaseDetailsRepository.save(purchase);
    }

    //Get a purchase by id
    public Optional<PurchaseDetailsModel> getDetailsById(Long id){
        return purchaseDetailsRepository.findById(id);
    }

    //Update a Details Purchase
    public PurchaseDetailsModel updateDetails(PurchaseDetailsModel request, Long id){
        PurchaseDetailsModel details = purchaseDetailsRepository.findById(id).get();

        details.setProduct(request.getProduct());
        details.setPurchasePrice(request.getPurchasePrice());
        details.setPurchase(request.getPurchase());
        details.setQuantity(request.getQuantity());
        details.setTotalPrice(request.getTotalPrice());

        return details;
    }

    //Delete Details by Id
    public Boolean deleteDetailsPurchase(Long id){
        try{
            purchaseDetailsRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }

}
