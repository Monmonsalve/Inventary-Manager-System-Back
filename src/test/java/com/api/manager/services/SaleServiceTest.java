package com.api.manager.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.api.manager.models.InventoryModel;
import com.api.manager.models.ProductModel;
import com.api.manager.models.SaleDetailsModel;
import com.api.manager.models.SaleModel;
import com.api.manager.models.StoreModel;
import com.api.manager.models.UserModel;
import com.api.manager.repositories.IInventoryRepository;
import com.api.manager.repositories.IProductRepository;
import com.api.manager.repositories.ISaleRepository;
import com.api.manager.repositories.IStoreRepository;
import com.api.manager.repositories.IUserRepository;

class SaleServiceTest {

    @Test
    void createSaleCalculatesTotalAndReducesStoreInventory() {
        ISaleRepository saleRepository = mock(ISaleRepository.class);
        IInventoryRepository inventoryRepository = mock(IInventoryRepository.class);
        IProductRepository productRepository = mock(IProductRepository.class);
        IStoreRepository storeRepository = mock(IStoreRepository.class);
        IUserRepository userRepository = mock(IUserRepository.class);

        StoreModel store = new StoreModel();
        store.setId(1L);
        UserModel user = new UserModel();
        user.setId(2L);
        ProductModel product = new ProductModel();
        product.setId(3L);
        product.setProductName("Keyboard");
        product.setPrice(new BigDecimal("25.00"));
        InventoryModel inventory = new InventoryModel();
        inventory.setProduct(product);
        inventory.setStore(store);
        inventory.setQuantity(10);
        SaleDetailsModel detail = new SaleDetailsModel();
        detail.setProduct(product);
        detail.setQuantity(2);
        SaleModel sale = new SaleModel();
        sale.setStore(store);
        sale.setUser(user);
        sale.setSaleDetails(List.of(detail));

        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(productRepository.findById(3L)).thenReturn(Optional.of(product));
        when(inventoryRepository.findByProductIdAndStoreId(3L, 1L)).thenReturn(Optional.of(inventory));
        when(saleRepository.save(any(SaleModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SaleService service = new SaleService(
                saleRepository, inventoryRepository, productRepository, storeRepository, userRepository);
        SaleModel result = service.create(sale);

        assertThat(inventory.getQuantity()).isEqualTo(8);
        assertThat(result.getTotalValue()).isEqualByComparingTo("50.00");
        assertThat(detail.getUnitPrice()).isEqualByComparingTo("25.00");
        assertThat(detail.getSale()).isSameAs(sale);
    }
}
