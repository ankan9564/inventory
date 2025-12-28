package com.example.inventory.test;

import com.example.inventory.dto.ProductDto;
import com.example.inventory.dto.StockRequest;
import com.example.inventory.model.Product;
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldThrowErrorWhenStockGoesNegative() {

        Product p = new Product();
        p.setSku("T-1");
        p.setName("Test Product");
        p.setStockQty(2);
        p.setMinStockLevel(1);
        productRepository.save(p);

        StockRequest req = new StockRequest("T-1", 5, "OUT");

        Assertions.assertThrows(RuntimeException.class,
                () -> productService.updateStock(req));
    }

    @Test
    void shouldTriggerLowStockAlert() {

        Product p = new Product();
        p.setSku("T-2");
        p.setName("Mouse");
        p.setStockQty(5);
        p.setMinStockLevel(3);
        productRepository.save(p);

        StockRequest req = new StockRequest("T-2", 3, "OUT");

        Product updated = productService.updateStock(req);

        Assertions.assertTrue(updated.getLowStock());
    }

    @Test
    void shouldAutoAssignCategory() {

        ProductDto dto = new ProductDto(
                "A1", "Cotton Shirt", "", 999, 5, 3
        );

        Product p = productService.addProduct(dto);

        Assertions.assertEquals("Clothing", p.getCategory());
    }
}
