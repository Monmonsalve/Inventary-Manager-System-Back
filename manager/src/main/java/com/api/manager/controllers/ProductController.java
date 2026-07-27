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

import com.api.manager.models.ProductModel;
import com.api.manager.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    //Get all product
    @GetMapping
    public ArrayList<ProductModel> getAllProducts(){
        return this.productService.getProduct();
    }

    //Save a new product
    @PostMapping
    public ProductModel saveProduct(@RequestBody ProductModel product){
        if (product != null){
            return this.productService.saveProduct(product);
        }else{
            return null;
        }
    } 

    //Get product By Id
    @GetMapping("/{id}")
    public Optional<ProductModel> getProductById(@PathVariable("id") Long id){
        return this.productService.getProductById(id);
    }

    //Update Product
    @PutMapping("/{id}")
    public ProductModel updateProduct(@RequestBody ProductModel request, @PathVariable("id") Long id){
        return this.productService.updateProductById(request, id);
    }

    //Delete a product
    @DeleteMapping("/{id}")
    public String deleteProductById(@PathVariable("id") Long id){
        boolean ok = productService.deleteProductById(id);
        if (ok) {
            return "Product deleted with id: " + id;
        }else{
            return "Could not delete product with id: " + id;
        }
    }

}
