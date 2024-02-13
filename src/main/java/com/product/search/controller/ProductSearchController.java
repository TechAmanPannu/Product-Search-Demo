package com.product.search.controller;

import com.product.search.entity.Product;
import com.product.search.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/products")
public class ProductSearchController {

    private final ProductService productService;

    @GetMapping("/search-by-liam")
    public ResponseEntity<Product> searchProducts(@RequestParam(value = "liam", required = true) String liam) {
        return ResponseEntity
                .ok(productService.getByLiam(liam));
    }
}
