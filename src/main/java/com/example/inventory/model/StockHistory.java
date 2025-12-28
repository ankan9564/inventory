package com.example.inventory.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class StockHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;
    private Integer quantityChange;
    private String type;
    private LocalDateTime timestamp = LocalDateTime.now();

    public StockHistory() {}

    public StockHistory(String sku, Integer quantityChange, String type) {
        this.sku = sku;
        this.quantityChange = quantityChange;
        this.type = type;
    }
}
