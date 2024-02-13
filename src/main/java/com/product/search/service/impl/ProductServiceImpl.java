package com.product.search.service.impl;

import com.product.search.entity.Product;
import com.product.search.service.ProductService;
import com.product.search.store.ProductStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductStore productStore;


    @Override
    public Product getByLiam(String liam) {
        return productStore.getByLiam(liam);
    }
}
