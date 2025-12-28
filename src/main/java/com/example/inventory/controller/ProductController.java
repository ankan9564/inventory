package com.example.inventory.controller;

import com.example.inventory.dto.ApiResponse;
import com.example.inventory.dto.ProductDto;
import com.example.inventory.dto.StockRequest;
import com.example.inventory.model.Product;
import com.example.inventory.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ApiResponse<Product> create(@RequestBody @Valid ProductDto dto) {
        return ApiResponse.success("Product created successfully", productService.addProduct(dto));
    }

    @GetMapping
    public ApiResponse<List<Product>> list() {
        return ApiResponse.success("Product list fetched", productService.listAll());
    }

    @GetMapping("/{sku}")
    public ApiResponse<Product> getBySku(@PathVariable String sku) {
        return ApiResponse.success("Product fetched", productService.getBySku(sku));
    }
    @GetMapping("/paged")
    public ApiResponse<Page<Product>> listPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return ApiResponse.success("Paged products fetched", productService.listPaged(page, size));
    }

    @PostMapping("/stock")
    public ApiResponse<Product> updateStock(@RequestBody @Valid StockRequest req) {

        Product product = productService.updateStock(req);

        String msg = product.getLowStock()
                ? "Stock updated. LOW STOCK ALERT triggered"
                : "Stock updated successfully";

        return ApiResponse.success(msg, product);
    }


    @GetMapping("/low-stock")
    public ApiResponse<List<Product>> lowStock() {
        return ApiResponse.success("Low stock products fetched", productService.getLowStockProducts());
    }
    @DeleteMapping("/{sku}")
    public ApiResponse<Void> delete(@PathVariable String sku) {
        productService.deleteBySku(sku);
        return ApiResponse.success("Product deleted successfully", null);
    }
    @DeleteMapping("/all")
    public ApiResponse<Void> deleteAll() {
        productService.deleteAllProducts();
        return ApiResponse.success("All products deleted successfully", null);
    }
    @DeleteMapping("/category/{category}")
    public ApiResponse<Void> deleteByCategory(@PathVariable String category) {
        productService.deleteByCategory(category);
        return ApiResponse.success(
                "All products deleted in category: " + category,
                null
        );
    }

    @GetMapping("/{sku}/reorder-suggestion")
    public ApiResponse<Integer> reorder(
            @PathVariable String sku,
            @RequestParam(defaultValue = "7") int leadDays) {
        return ApiResponse.success("Reorder suggestion generated",
                productService.suggestReorderQty(sku, leadDays));
    }
}

