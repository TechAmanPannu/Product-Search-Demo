package com.product.search.model.response;

import com.product.search.entity.Product;
import lombok.Data;

import java.util.Map;

@Data
public class ProductResponseModel {

    private String liam;
    private Long id;

    public static ProductResponseModel of(Product product) {
        ProductResponseModel productResponseModel = new ProductResponseModel();
        productResponseModel.setLiam(product.getLiam());
       productResponseModel.setId(product.getId());
        return productResponseModel;
    }
}
