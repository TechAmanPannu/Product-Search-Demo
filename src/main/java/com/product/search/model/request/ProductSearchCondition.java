package com.product.search.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductSearchCondition implements Serializable {

    private static final long serialVersionUID = 1025694212993269346L;

    private String property;
    private String operator;
    private String value;

}
