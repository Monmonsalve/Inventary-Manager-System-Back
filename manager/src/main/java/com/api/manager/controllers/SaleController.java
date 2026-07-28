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

import com.api.manager.models.SaleModel;
import com.api.manager.services.SaleService;

@RestController
@RequestMapping("/sale")
public class SaleController {
    
    @Autowired
    private SaleService saleService;

    //Route get all Sale
    @GetMapping
    public ArrayList<SaleModel> getSale(){
        return this.saleService.getSale();
    }

    //Route Create Sale
    @PostMapping
    public SaleModel saveSale(@RequestBody SaleModel sale){
        if(sale != null){
            return this.saleService.saveSale(sale);
        }else{
            return null;
        }
    }

    //Route Get Sale By Id
    @GetMapping(path = "/{id}")
    public Optional<SaleModel> getSaleById(@PathVariable("id") Long id){
        return this.saleService.getSaleById(id);
    }

    //Route Update Sale By Id
    @PutMapping(path = "/{id}")
    public SaleModel updateSale(@RequestBody SaleModel request, @PathVariable("id") Long id){
        return this.saleService.updateSaleById(request, id);
    }

    //Route Delete Sale By Id
    @DeleteMapping
    public String deleteSaleById(@PathVariable("id") Long id){
        boolean ok = this.saleService.deleteSale(id);
        if(ok){
            return "Sale deleted with id: " + id;
        }else{
            return "Could not deleted Sale with id: " + id;
        }
    }

}
