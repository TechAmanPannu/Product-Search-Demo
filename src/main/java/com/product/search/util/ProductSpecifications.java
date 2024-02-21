package com.product.search.util;

import com.product.search.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Subquery;

public interface ProductSpecifications {


    static Specification<Product> withLiam(String liam) {

        return (root, query, builder) -> {
            return builder.equal(root.get("liam"), liam);
        };
    }


    static Specification<Product> with(String property, String operator, String value) {
        return (root, query, builder) -> {
            if (operator.equals("EQ")) {
                return builder.equal(root.get(property), value);
            } else if (operator.equals("BEGINS_WITH")) {
                return builder.like(root.get(property), value + "%");
            } else { // In case of CONTAINS
                return builder.like(root.get(property), "%" + value + "%");
            }
        };
    }

}
