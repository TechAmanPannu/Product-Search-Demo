package com.product.search.model;

import com.product.search.enums.ProductSearchQueryType;
import lombok.Getter;


@Getter
public class ProductSearchConfigModel {

    private String columnExpression;
    private final String operator;
    private String valueExpression;
    private boolean lowerIndex;
    private boolean tsQuery;
    private final ProductSearchQueryType requiredQueryType;
    private int orderPriority;

    public ProductSearchConfigModel(String operator, ProductSearchQueryType requiredQueryType, int orderPriority) {
        this.operator = operator;
        this.requiredQueryType = requiredQueryType;
        this.orderPriority = orderPriority;
    }

    public ProductSearchConfigModel(String operator, boolean lowerIndex, boolean tsQuery, ProductSearchQueryType requiredQueryType, int orderPriority) {
        this( operator, requiredQueryType, orderPriority);
        this.lowerIndex = lowerIndex;
        this.tsQuery = tsQuery;
    }

    public ProductSearchConfigModel(String columnExpression, String operator, ProductSearchQueryType requiredQueryType, int orderPriority) {
        this(operator, requiredQueryType, orderPriority);
        this.columnExpression = columnExpression;
    }

    public ProductSearchConfigModel(String column, String operator, String valueExpression, ProductSearchQueryType requiredQueryType, int orderPriority) {
        this(column, operator, requiredQueryType, orderPriority);
        this.valueExpression = valueExpression;
    }

    public ProductSearchConfigModel(String column, String operator, String valueExpression, boolean lowerIndex, ProductSearchQueryType requiredQueryType, int orderPriority) {
        this(column, operator, valueExpression, requiredQueryType, orderPriority);
        this.lowerIndex = lowerIndex;
    }
    public ProductSearchConfigModel(String column, String operator, String valueExpression, boolean lowerIndex, boolean tsQuery, ProductSearchQueryType requiredQueryType, int orderPriority) {
        this(column, operator, valueExpression, lowerIndex, requiredQueryType, orderPriority);
        this.tsQuery = tsQuery;
    }

}
