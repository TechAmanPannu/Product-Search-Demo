package com.product.search.enums;

import java.lang.reflect.Array;
import java.util.*;

public enum ProductSearchQueryType {

    BASIC_QUERY(3),
    SUB_QUERY(2),

    JOIN_QUERY(1);

    private int rank;

    ProductSearchQueryType(int rank) {
        this.rank = rank;
    }

    public  int getPriority() {
        return this.rank;
    }


    public static ProductSearchQueryType findTopRankedQueryType(List<ProductSearchQueryType> queryTypes) {
        return queryTypes.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(ProductSearchQueryType::getPriority))
                .findFirst().orElse(ProductSearchQueryType.BASIC_QUERY);
    }

}
