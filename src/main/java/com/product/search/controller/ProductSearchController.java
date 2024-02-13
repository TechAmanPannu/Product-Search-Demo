package com.product.search.controller;

import com.product.search.entity.Product;
import com.product.search.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/products")
public class ProductSearchController {

    private final ProductService productService;

    @GetMapping("/liams/{liamId}")
    public ResponseEntity<Product> getByLiam(@PathVariable(value = "liamId", required = true) String liam) {
        return ResponseEntity
                .ok(productService.getByLiam(liam));
    }


}
