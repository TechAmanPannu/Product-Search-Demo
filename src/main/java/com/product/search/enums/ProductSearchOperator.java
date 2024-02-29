package com.product.search.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ProductSearchOperator {

    EQ("eq"),
    CONTAINS("contains");

    private String value;

     ProductSearchOperator(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ProductSearchOperator from(String value) {
         if(value == null || value.isEmpty()) {
             return null;
         }
        for (ProductSearchOperator productSearchOperator : ProductSearchOperator.values()) {
            if(productSearchOperator.value.equals(value.toLowerCase())) {
                return productSearchOperator;
            }
        }
        return null;
    }



}
