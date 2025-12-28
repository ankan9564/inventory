package com.example.inventory.repository;

import com.example.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySku(String sku);
    List<Product> findByCategoryIgnoreCase(String category);
    void deleteByCategoryIgnoreCase(String category);

}
