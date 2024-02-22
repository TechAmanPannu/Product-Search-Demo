package com.product.search.store;

import com.product.search.entity.Product;
import com.product.search.model.request.ProductSearchCondition;
import com.product.search.model.request.ProductSearchRequest;
import com.product.search.repository.ProductRepository;
import com.product.search.util.ProductSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class ProductStore {

    private final ProductRepository productRepository;

    public Product getByLiam(String liam) {
        return productRepository.findOne(ProductSpecifications.withLiam(liam)).orElseGet(null);
    }


    public Page<Product> searchProducts(ProductSearchRequest productSearchRequest) {
        Specification<Product> specification = null;
        for (ProductSearchCondition condition : productSearchRequest.getConditions()) {
            if (specification == null) {
                specification = ProductSpecifications.with(condition.getProperty().getColumnName(), condition.getProperty().getOperator(condition.getOperator()), condition.getValue());
            } else {
                specification = specification.
                        and(ProductSpecifications.with(condition.getProperty().getColumnName(), condition.getProperty().getOperator(condition.getOperator()), condition.getValue()));
            }
        }
        return productRepository.findAll(specification, Pageable.ofSize(50));
    }
}
