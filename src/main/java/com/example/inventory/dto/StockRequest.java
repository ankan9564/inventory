package com.example.inventory.dto;

import jakarta.validation.constraints.*;

public record StockRequest(

        @NotBlank(message = "SKU is required")
        String sku,

        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be greater than zero")
        Integer quantity,

        @NotBlank(message = "Type is required (IN / OUT)")
        String type
) {}
