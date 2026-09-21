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

import com.api.manager.models.SaleModel;
import com.api.manager.services.SaleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping({"/sales", "/sale"})
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public List<SaleModel> getSales() {
        return saleService.getSales();
    }

    @GetMapping("/{id}")
    public SaleModel getSale(@PathVariable Long id) {
        return saleService.getById(id);
    }

    @PostMapping
    public ResponseEntity<SaleModel> createSale(@Valid @RequestBody SaleModel sale) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saleService.create(sale));
    }

    @PutMapping("/{id}")
    public SaleModel updateSale(@PathVariable Long id, @RequestBody SaleModel request) {
        return saleService.update(id, request);
    }
}
