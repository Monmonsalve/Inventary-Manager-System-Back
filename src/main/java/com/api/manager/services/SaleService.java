package com.api.manager.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.manager.exception.BusinessException;
import com.api.manager.exception.ResourceNotFoundException;
import com.api.manager.models.InventoryModel;
import com.api.manager.models.ProductModel;
import com.api.manager.models.SaleDetailsModel;
import com.api.manager.models.SaleModel;
import com.api.manager.repositories.IInventoryRepository;
import com.api.manager.repositories.IProductRepository;
import com.api.manager.repositories.ISaleRepository;
import com.api.manager.repositories.IStoreRepository;
import com.api.manager.repositories.IUserRepository;

@Service
public class SaleService {

    private final ISaleRepository saleRepository;
    private final IInventoryRepository inventoryRepository;
    private final IProductRepository productRepository;
    private final IStoreRepository storeRepository;
    private final IUserRepository userRepository;

    public SaleService(ISaleRepository saleRepository, IInventoryRepository inventoryRepository,
            IProductRepository productRepository, IStoreRepository storeRepository, IUserRepository userRepository) {
        this.saleRepository = saleRepository;
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<SaleModel> getSales() {
        return saleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public SaleModel getById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id " + id));
    }

    @Transactional
    public SaleModel create(SaleModel sale) {
        validateReferences(sale);
        sale.setDate(sale.getDate() == null ? LocalDateTime.now() : sale.getDate());

        BigDecimal total = BigDecimal.ZERO;
        for (SaleDetailsModel detail : sale.getSaleDetails()) {
            if (detail.getProduct() == null || detail.getProduct().getId() == null) {
                throw new BusinessException("Product id is required for every sale detail");
            }
            ProductModel product = productRepository.findById(detail.getProduct().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Product not found with id " + detail.getProduct().getId()));
            InventoryModel inventory = inventoryRepository
                    .findByProductIdAndStoreId(product.getId(), sale.getStore().getId())
                    .orElseThrow(() -> new BusinessException(
                            "Product " + product.getId() + " is not registered in this store"));

            if (detail.getQuantity() == null || detail.getQuantity() <= 0) {
                throw new BusinessException("Sale quantities must be greater than zero");
            }
            if (inventory.getQuantity() < detail.getQuantity()) {
                throw new BusinessException("Insufficient stock for product " + product.getProductName());
            }

            inventory.setQuantity(inventory.getQuantity() - detail.getQuantity());
            detail.setProduct(product);
            detail.setUnitPrice(product.getPrice());
            detail.setSubPrice(product.getPrice().multiply(BigDecimal.valueOf(detail.getQuantity())));
            detail.setSale(sale);
            total = total.add(detail.getSubPrice());
        }

        sale.setTotalValue(total);
        return saleRepository.save(sale);
    }

    @Transactional
    public SaleModel update(Long id, SaleModel request) {
        SaleModel sale = getById(id);
        sale.setDate(request.getDate() == null ? sale.getDate() : request.getDate());
        return saleRepository.save(sale);
    }

    @Transactional
    public void delete(Long id) {
        throw new BusinessException("Completed sales cannot be deleted because they affect inventory");
    }

    private void validateReferences(SaleModel sale) {
        if (sale.getStore() == null || sale.getStore().getId() == null) {
            throw new BusinessException("Store id is required");
        }
        if (sale.getUser() == null || sale.getUser().getId() == null) {
            throw new BusinessException("User id is required");
        }
        if (sale.getSaleDetails() == null || sale.getSaleDetails().isEmpty()) {
            throw new BusinessException("A sale must contain at least one product");
        }
        sale.setStore(storeRepository.findById(sale.getStore().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Store not found with id " + sale.getStore().getId())));
        sale.setUser(userRepository.findById(sale.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + sale.getUser().getId())));
    }
}
