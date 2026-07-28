package com.api.manager.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.manager.models.SaleModel;
import com.api.manager.repositories.ISaleRepository;

@Service
public class SaleService {
    
    @Autowired
    ISaleRepository saleRepository;

    //Get all sale
    public ArrayList<SaleModel> getSale(){
        return (ArrayList<SaleModel>) saleRepository.findAll();
    }

    //Save Sale
    public SaleModel saveSale(SaleModel sale){
        return saleRepository.save(sale);
    }

    //Get sale By Id
    public Optional<SaleModel> getSaleById(Long id){
        return saleRepository.findById(id);
    }

    //Update Sale By Id
    public SaleModel updateSaleById(SaleModel request, Long id){
        SaleModel sale = saleRepository.findById(id).get();

        sale.setStore(request.getStore());
        sale.setUser(request.getUser());
        sale.setDate(request.getDate());
        sale.setSaleDetails(request.getSaleDetails());
        sale.setTotalValue(request.getTotalValue());
        
        return sale;
    }

    //Delete Sale By Id
    public Boolean deleteSale (Long id){
        try{
            saleRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }

}
