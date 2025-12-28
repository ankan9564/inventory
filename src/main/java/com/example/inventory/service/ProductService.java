package com.example.inventory.service;

import com.example.inventory.dto.ProductDto;
import com.example.inventory.dto.StockRequest;
import com.example.inventory.model.Product;
import com.example.inventory.model.StockHistory;
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.repository.StockHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final StockHistoryRepository stockHistoryRepository;
    private final EmailService emailService;
    public ProductService(ProductRepository productRepository,
                          StockHistoryRepository stockHistoryRepository,
                          EmailService emailService) {
        this.productRepository = productRepository;
        this.stockHistoryRepository = stockHistoryRepository;
        this.emailService = emailService;
    }

    // Auto-category logic
    public String autoAssignCategory(String name) {
        String s = name.toLowerCase();
        if (s.contains("shirt") || s.contains("jeans")) return "Clothing";
        if (s.contains("mouse") || s.contains("keyboard")) return "Electronics";
        if (s.contains("bottle")) return "Kitchen";
        if (s.contains("book")) return "Stationery";
        return "Uncategorized";
    }

    public Product addProduct(ProductDto dto) {

        String category = (dto.category() == null || dto.category().isBlank())
                ? autoAssignCategory(dto.name())
                : dto.category();

        Product p = new Product();
        p.setSku(dto.sku());
        p.setName(dto.name());
        p.setCategory(category);
        p.setPrice(BigDecimal.valueOf(dto.price()));
        p.setStockQty(dto.stockQty());
        p.setMinStockLevel(dto.minStockLevel());

        return productRepository.save(p);
    }

    public List<Product> listAll() {
        return productRepository.findAll();
    }

    public Product updateStock(StockRequest req) {

        Product product = productRepository.findBySku(req.sku())
                .orElseThrow(() -> new RuntimeException("Product not found for SKU: " + req.sku()));

        int qty = product.getStockQty() == null ? 0 : product.getStockQty();
        String operation = req.type().trim().toUpperCase();

        switch (operation) {
            case "IN" -> qty += req.quantity();
            case "OUT" -> {
                if (qty - req.quantity() < 0)
                    throw new RuntimeException("Stock cannot go below zero for SKU: " + req.sku());
                qty -= req.quantity();
            }
            default -> throw new RuntimeException("Invalid stock operation type. Use IN or OUT");
        }

        product.setStockQty(qty);
        productRepository.save(product);

        stockHistoryRepository.save(
                new StockHistory(req.sku(),
                        operation.equals("IN") ? req.quantity() : -req.quantity(),
                        operation)
        );

        // store alert state here
        product.setLowStock(qty < product.getMinStockLevel());

        if (product.getLowStock()) {
            System.out.println("LOW STOCK ALERT → " + product.getSku());

            try {
                emailService.sendLowStockAlert(
                        product.getSku(),
                        product.getName(),
                        qty,
                        product.getMinStockLevel()
                );
            } catch (Exception ex) {
                throw new RuntimeException("Low stock alert triggered but email failed: " + ex.getMessage());
            }

        }

        return product;
    }

    @Transactional
    public void deleteAllProducts() {
        stockHistoryRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Transactional
    public void deleteByCategory(String category) {

        List<Product> products = productRepository.findByCategoryIgnoreCase(category);

        if (products.isEmpty()) {
            throw new RuntimeException("No products found in category: " + category);
        }

        productRepository.deleteByCategoryIgnoreCase(category);
    }
    //pagination
    public Page<Product> listPaged(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }


    public List<Product> getLowStockProducts() {
        return productRepository.findAll().stream()
                .filter(p -> p.getStockQty() < p.getMinStockLevel())
                .toList();
    }
    public Product getBySku(String sku) {
        return productRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Product not found: " + sku));
    }
    @Transactional
    public void deleteBySku(String sku) {

        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Product not found: " + sku));

        productRepository.delete(product);
    }


    // Smart reorder suggestion
    public Integer suggestReorderQty(String sku, int leadDays) {

        Integer avg = stockHistoryRepository.findAverageDailyUsage(sku);
        if (avg == null || avg == 0) return leadDays;

        return avg * leadDays;
    }
}
