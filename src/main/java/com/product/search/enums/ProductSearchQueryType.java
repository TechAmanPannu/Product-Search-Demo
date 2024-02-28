package com.product.search.enums;

import java.lang.reflect.Array;
import java.util.*;

public enum ProductSearchQueryType {

    BASIC_QUERY(3),
    SUB_QUERY(2),

    JOIN_QUERY(1);

    private int priority;

    ProductSearchQueryType(int priority) {
        this.priority = priority;
    }

    public  int getPriority() {
        return this.priority;
    }


    public static ProductSearchQueryType findHighPriorityQueryType(List<ProductSearchQueryType> queryTypes) {
        System.out.println(queryTypes);
        return queryTypes.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(ProductSearchQueryType::getPriority))
                .findFirst().orElse(ProductSearchQueryType.BASIC_QUERY);
    }

}
