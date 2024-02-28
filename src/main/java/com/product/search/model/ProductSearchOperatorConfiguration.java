package com.product.search.model;

import lombok.Getter;


@Getter
public class ProductSearchOperatorConfiguration {

    private String columnExpression;
    private String operator;
    private String valueExpression;
    private boolean lowerIndex;
    private boolean tsQuery;

    public ProductSearchOperatorConfiguration(String operator) {
        this.operator = operator;
    }
    public ProductSearchOperatorConfiguration(String columnExpression, String operator) {
        this(operator);
        this.columnExpression = columnExpression;
    }

    public ProductSearchOperatorConfiguration(String column, String operator, boolean lowerIndex) {
        this(column, operator);
        this.lowerIndex = lowerIndex;
    }

    public ProductSearchOperatorConfiguration(String column, String operator, String valueExpression) {
        this(column, operator);
        this.valueExpression = valueExpression;
    }

    public ProductSearchOperatorConfiguration(String column, String operator, String valueExpression, boolean lowerIndex) {
        this(column, operator, valueExpression);
        this.lowerIndex = lowerIndex;
    }
    public ProductSearchOperatorConfiguration(String column, String operator, String valueExpression, boolean lowerIndex, boolean tsQuery) {
        this(column, operator, valueExpression, lowerIndex);
        this.tsQuery = tsQuery;
    }

}
