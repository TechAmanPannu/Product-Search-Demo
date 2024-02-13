package com.product.search.store;

import com.product.search.entity.Product;
import com.product.search.repository.ProductRepository;
import com.product.search.util.ProductSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductStore {

    private final ProductRepository productRepository;

    public Product getByLiam(String liam) {
        List<Product> products = productRepository.findAll(ProductSpecifications.withLiam(liam));
        if(products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Invalid liam");
        }
        return products.get(0);
    }
}
