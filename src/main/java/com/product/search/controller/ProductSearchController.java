package com.product.search.controller;

import com.product.search.entity.Product;
import com.product.search.model.request.ProductSearchRequest;
import com.product.search.model.response.ProductResponseModel;
import com.product.search.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/products")
public class ProductSearchController {

    private final ProductService productService;

    @GetMapping("/liams/{liamId}")
    public ResponseEntity<ProductResponseModel> getByLiam(@PathVariable(value = "liamId", required = true) String liam) {
        return ResponseEntity
                .ok(productService.getByLiam(liam));
    }


    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductResponseModel>> search(@RequestBody ProductSearchRequest productSearchRequest) {
       return ResponseEntity
               .ok(productService.search(productSearchRequest));
    }

}
