package com.product.search.service.impl;

import com.product.search.entity.Product;
import com.product.search.model.request.ProductSearchCondition;
import com.product.search.model.request.ProductSearchRequest;
import com.product.search.model.response.ProductResponseModel;
import com.product.search.service.ProductService;
import com.product.search.store.ProductStore;
import com.product.search.util.ProductSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductStore productStore;

    @Override
    public ProductResponseModel getByLiam(String liam) {
        return ProductResponseModel.of(productStore.getByLiam(liam));
    }

    @Override
    public List<ProductResponseModel> search(ProductSearchRequest productSearchRequest) {
        if (productSearchRequest == null || productSearchRequest.getConditions() == null || productSearchRequest.getConditions().isEmpty()) {
            return List.of();
        }
        return productStore.searchProducts(productSearchRequest)
                .stream()
                .map(ProductResponseModel::of)
                .collect(Collectors.toList());
    }
}
