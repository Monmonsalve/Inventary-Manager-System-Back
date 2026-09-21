package com.api.manager.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.manager.models.SupplierModel;
import com.api.manager.repositories.ISupplierRepository;

@Service
public class SupplierService {
    
    @Autowired
    ISupplierRepository supplierRepository;

    public ArrayList<SupplierModel> getSupplier(){
        return (ArrayList<SupplierModel>) supplierRepository.findAll();
    }

    public SupplierModel saveSupplier(SupplierModel supplier){
        return supplierRepository.save(supplier);
    }

    public Optional<SupplierModel> getSupplierById(Long id){
        return supplierRepository.findById(id);
    }

    public SupplierModel updateSupplier(SupplierModel request,  Long id){
        SupplierModel supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new com.api.manager.exception.ResourceNotFoundException("Supplier not found with id " + id));

        supplier.setEmail(request.getEmail());
        supplier.setAddress(request.getAddress());
        supplier.setCompany(request.getCompany());
        supplier.setContactName(request.getContactName());
        supplier.setPhoneNumber(request.getPhoneNumber());

        return supplierRepository.save(supplier);
    } 

    public Boolean deleteSupplier(Long id){
        try{
            supplierRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }

}
