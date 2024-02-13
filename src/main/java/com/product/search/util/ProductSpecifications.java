package com.product.search.util;

import com.product.search.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public interface ProductSpecifications {

    static Specification<Product> withLiam(String liam) {

        return (root, query, builder) -> {
            return builder.equal(root.get("liam"), liam);
        };
    }
}
