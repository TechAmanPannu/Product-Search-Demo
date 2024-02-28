package com.product.search.model;

import com.product.search.enums.ProductSearchQueryType;
import lombok.Getter;


@Getter
public class ProductSearchOperatorConfiguration {

    private String columnExpression;
    private final String operator;
    private String valueExpression;
    private boolean lowerIndex;
    private boolean tsQuery;
    private final ProductSearchQueryType requiredQueryType;

    public ProductSearchOperatorConfiguration(String operator, ProductSearchQueryType requiredQueryType) {
        this.operator = operator;
        this.requiredQueryType = requiredQueryType;
    }
    public ProductSearchOperatorConfiguration(String columnExpression, String operator, ProductSearchQueryType requiredQueryType) {
        this(operator, requiredQueryType);
        this.columnExpression = columnExpression;
    }

    public ProductSearchOperatorConfiguration(String column, String operator, String valueExpression, ProductSearchQueryType requiredQueryType) {
        this(column, operator, requiredQueryType);
        this.valueExpression = valueExpression;
    }

    public ProductSearchOperatorConfiguration(String column, String operator, String valueExpression, boolean lowerIndex, ProductSearchQueryType requiredQueryType) {
        this(column, operator, valueExpression, requiredQueryType);
        this.lowerIndex = lowerIndex;
    }
    public ProductSearchOperatorConfiguration(String column, String operator, String valueExpression, boolean lowerIndex, boolean tsQuery, ProductSearchQueryType requiredQueryType) {
        this(column, operator, valueExpression, lowerIndex, requiredQueryType);
        this.tsQuery = tsQuery;
    }

}
