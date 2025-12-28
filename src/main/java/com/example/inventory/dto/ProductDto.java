package com.example.inventory.dto;

import jakarta.validation.constraints.*;

public record ProductDto(

        @NotBlank(message = "SKU is required")
        String sku,

        @NotBlank(message = "Product name is required")
        String name,

        String category,

        int price,

        @NotNull(message = "Stock quantity is required")
        @Min(value = 0, message = "Stock cannot be negative")
        Integer stockQty,

        @NotNull(message = "Minimum stock level is required")
        @Min(value = 0, message = "Minimum stock level cannot be negative")
        Integer minStockLevel
) {}
