package com.example.inventory.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String sku;

    @Column(nullable=false)
    private String name;

    private String category;

    @Column(nullable=false)
    private BigDecimal price;

    @Column(nullable=false)
    private Integer stockQty;

    @Column(nullable=false)
    private Integer minStockLevel;
    @Transient
    private Boolean lowStock;
    public Boolean getLowStock() { return lowStock; }
    public void setLowStock(Boolean lowStock) { this.lowStock = lowStock; }

    public Product() {}

    // Getters & Setters
    public Long getId() { return id; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getStockQty() { return stockQty; }
    public void setStockQty(Integer stockQty) { this.stockQty = stockQty; }

    public Integer getMinStockLevel() { return minStockLevel; }
    public void setMinStockLevel(Integer minStockLevel) { this.minStockLevel = minStockLevel; }
}
