package com.api.manager.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.manager.models.ProductModel;
import com.api.manager.repositories.IProductRepository;

@Service
public class ProductService {

    @Autowired
    private IProductRepository productRepository;

    //Get all product
    public ArrayList<ProductModel> getProduct(){
        return (ArrayList<ProductModel>) productRepository.findAll();
    } 

    //Create a Product
    public ProductModel saveProduct(ProductModel product){
        return productRepository.save(product);
    }
    
    //Get product By Id
    public Optional<ProductModel> getProductById(Long id){
        return productRepository.findById(id);
    }

    //Update Product
    public ProductModel updateProductById(ProductModel request, Long id){
        ProductModel product = productRepository.findById(id).get();

        product.setProductName(request.getProductName());
        product.setStock(request.getStock());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        product.setSupplier(request.getSupplier());

        return productRepository.save(product);
    }

    //Delete product
    public boolean deleteProductById(Long id){
        try{
            productRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    } 

}
