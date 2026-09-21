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
import com.api.manager.models.PurchaseDetailsModel;
import com.api.manager.models.PurchaseModel;
import com.api.manager.repositories.IInventoryRepository;
import com.api.manager.repositories.IProductRepository;
import com.api.manager.repositories.IPurchaseRepository;
import com.api.manager.repositories.IStoreRepository;
import com.api.manager.repositories.ISupplierRepository;
import com.api.manager.repositories.IUserRepository;

@Service
public class PurchaseService {

    private final IPurchaseRepository purchaseRepository;
    private final IInventoryRepository inventoryRepository;
    private final IProductRepository productRepository;
    private final IStoreRepository storeRepository;
    private final ISupplierRepository supplierRepository;
    private final IUserRepository userRepository;

    public PurchaseService(IPurchaseRepository purchaseRepository, IInventoryRepository inventoryRepository,
            IProductRepository productRepository, IStoreRepository storeRepository,
            ISupplierRepository supplierRepository, IUserRepository userRepository) {
        this.purchaseRepository = purchaseRepository;
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
        this.supplierRepository = supplierRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<PurchaseModel> getPurchases() {
        return purchaseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PurchaseModel getById(Long id) {
        return purchaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found with id " + id));
    }

    @Transactional
    public PurchaseModel create(PurchaseModel purchase) {
        validateReferences(purchase);
        purchase.setDate(purchase.getDate() == null ? LocalDateTime.now() : purchase.getDate());

        BigDecimal total = BigDecimal.ZERO;
        for (PurchaseDetailsModel detail : purchase.getPurchaseDetails()) {
            if (detail.getProduct() == null || detail.getProduct().getId() == null) {
                throw new BusinessException("Product id is required for every purchase detail");
            }
            ProductModel product = productRepository.findById(detail.getProduct().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Product not found with id " + detail.getProduct().getId()));
            if (detail.getQuantity() == null || detail.getQuantity() <= 0
                    || detail.getPurchasePrice() == null || detail.getPurchasePrice().signum() <= 0) {
                throw new BusinessException("Purchase quantity and price must be greater than zero");
            }

            InventoryModel inventory = inventoryRepository
                    .findByProductIdAndStoreId(product.getId(), purchase.getStore().getId())
                    .orElseGet(() -> newInventory(product, purchase));
            inventory.setQuantity(inventory.getQuantity() + detail.getQuantity());
            inventoryRepository.save(inventory);

            detail.setProduct(product);
            detail.setTotalPrice(detail.getPurchasePrice().multiply(BigDecimal.valueOf(detail.getQuantity())));
            detail.setPurchase(purchase);
            total = total.add(detail.getTotalPrice());
        }

        purchase.setTotalValue(total);
        return purchaseRepository.save(purchase);
    }

    @Transactional
    public PurchaseModel update(Long id, PurchaseModel request) {
        PurchaseModel purchase = getById(id);
        purchase.setDate(request.getDate() == null ? purchase.getDate() : request.getDate());
        return purchaseRepository.save(purchase);
    }

    @Transactional
    public void delete(Long id) {
        throw new BusinessException("Completed purchases cannot be deleted because they affect inventory");
    }

    private InventoryModel newInventory(ProductModel product, PurchaseModel purchase) {
        InventoryModel inventory = new InventoryModel();
        inventory.setProduct(product);
        inventory.setStore(purchase.getStore());
        inventory.setQuantity(0);
        return inventory;
    }

    private void validateReferences(PurchaseModel purchase) {
        if (purchase.getStore() == null || purchase.getStore().getId() == null) {
            throw new BusinessException("Store id is required");
        }
        if (purchase.getSupplier() == null || purchase.getSupplier().getId() == null) {
            throw new BusinessException("Supplier id is required");
        }
        if (purchase.getUser() == null || purchase.getUser().getId() == null) {
            throw new BusinessException("User id is required");
        }
        if (purchase.getPurchaseDetails() == null || purchase.getPurchaseDetails().isEmpty()) {
            throw new BusinessException("A purchase must contain at least one product");
        }

        purchase.setStore(storeRepository.findById(purchase.getStore().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Store not found with id " + purchase.getStore().getId())));
        purchase.setSupplier(supplierRepository.findById(purchase.getSupplier().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Supplier not found with id " + purchase.getSupplier().getId())));
        purchase.setUser(userRepository.findById(purchase.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + purchase.getUser().getId())));
    }
}
