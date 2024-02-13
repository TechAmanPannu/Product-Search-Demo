package com.product.search.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/products")
public class ProductSearchController {

    @GetMapping("/search")
    public ResponseEntity<String> searchProducts() {
        return ResponseEntity
                .ok("Ok");
    }
}
