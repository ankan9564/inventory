package com.example.inventory.controller;

import com.example.inventory.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UiController {

    private final ProductService productService;

    public UiController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("products", productService.listAll());
        return "dashboard";
    }
}
