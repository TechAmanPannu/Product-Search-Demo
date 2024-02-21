package com.product.search.model.response;

import com.product.search.entity.Product;
import lombok.Data;

import java.util.Map;

@Data
public class ProductResponseModel {

    private String liam;
//    private String ahCode;
//    private String mchCode;

    public static ProductResponseModel of(Product product) {
        ProductResponseModel productResponseModel = new ProductResponseModel();
        productResponseModel.setLiam(product.getLiam());
//        productResponseModel.setAhCode(product.getAhCode());
//        productResponseModel.setMchCode(product.getMchCode());
        return productResponseModel;
    }
}
