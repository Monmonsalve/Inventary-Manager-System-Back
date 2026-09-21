package com.api.manager.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.manager.models.PurchaseModel;
import com.api.manager.services.PurchaseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping({"/purchases", "/purchase"})
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public List<PurchaseModel> getPurchases() {
        return purchaseService.getPurchases();
    }

    @GetMapping("/{id}")
    public PurchaseModel getPurchase(@PathVariable Long id) {
        return purchaseService.getById(id);
    }

    @PostMapping
    public ResponseEntity<PurchaseModel> createPurchase(@Valid @RequestBody PurchaseModel purchase) {
        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseService.create(purchase));
    }

    @PutMapping("/{id}")
    public PurchaseModel updatePurchase(@PathVariable Long id, @RequestBody PurchaseModel request) {
        return purchaseService.update(id, request);
    }
}
