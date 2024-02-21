package com.product.search.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProductSearchRequest implements Serializable {

    private static final long serialVersionUID = -6530293410250349159L;

    private boolean allConditionsMatch;
    private List<ProductSearchCondition> conditions;
}
