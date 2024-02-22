package com.product.search.controller;

import com.ld.editorial.api.ProductsApi;
import com.ld.editorial.model.SearchProductRequest;
import com.ld.editorial.model.SearchProductResponse;
import org.springframework.http.ResponseEntity;

public class ProductController implements ProductsApi {
    @Override
    public ResponseEntity<SearchProductResponse> searchProducts(SearchProductRequest searchProductRequest) {
        return null;
    }
}
