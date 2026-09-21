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

import com.api.manager.models.SupplierModel;
import com.api.manager.services.SupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public ArrayList<SupplierModel> getSupplier(){
        return (ArrayList<SupplierModel>) this.supplierService.getSupplier();
    }

    @PostMapping
    public SupplierModel saveSupplier(@RequestBody SupplierModel supplier){
        if (supplier != null){
            return this.supplierService.saveSupplier(supplier);
        }else{
            return null;
        }
    }

    @GetMapping("/{id}")
    public Optional<SupplierModel> getSupplierById(@PathVariable("id") Long id){
        return supplierService.getSupplierById(id);
    }

    @PutMapping("/{id}")
    public SupplierModel updateSupplier(@RequestBody SupplierModel request, Long id){
        return this.supplierService.updateSupplier(request, id);
    }

    @DeleteMapping("/{id}")
    public String deleteSupplier(Long id){
        boolean ok = this.supplierService.deleteSupplier(id);
        if(ok){
            return "Supplier Deleted with id: " + id;
        }else{
            return "Could not Deleted with id: " + id;
        }
    }
}
