package com.product.search.model.request;

import com.product.search.enums.ProductSearchOperator;
import com.product.search.enums.ProductSearchProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class ProductSearchCondition implements Serializable {

    private static final long serialVersionUID = 1025694212993269346L;

    private ProductSearchProperty property;
    private ProductSearchOperator operator;
    private String value;
    private ProductSearchValueModel values;

}
