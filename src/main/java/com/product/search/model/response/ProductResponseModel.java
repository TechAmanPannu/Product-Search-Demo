package com.product.search.model.response;

import com.product.search.entity.Category;
import com.product.search.entity.Product;
import com.product.search.util.query.JsonUtil;
import lombok.Data;

import java.util.Map;

@Data
public class ProductResponseModel {

    private String liam;
    private Long id;
    private String status;
    private CategoryResponseModel category;
    private Map<String, Object> enrichment;
    private Map<String, Object> data;
    private String updatedAt;
    private String insertedAt;
    private String ahCode;
    private String mchCode;
    private String brandCode;
    private Map<String, Object> variantData;

    public static ProductResponseModel of(Product product) {
        ProductResponseModel productResponseModel = new ProductResponseModel();
        productResponseModel.setLiam(product.getLiam());
       productResponseModel.setId(product.getId());
       productResponseModel.setStatus(product.getStatus());
       if(product.getCategory() != null) {
           Category category = product.getCategory();
           productResponseModel.setCategory(new CategoryResponseModel(category.getId(), (String) JsonUtil.getMapFromJson(category.getName()).get("en"), category.getType()));
       }
       productResponseModel.setData(JsonUtil.getMapFromJson(product.getData()));
       productResponseModel.setEnrichment(JsonUtil.getMapFromJson(product.getEnrichment()));
       productResponseModel.setInsertedAt(product.getInsertedAt());
       productResponseModel.setUpdatedAt(product.getUpdatedAt());
       productResponseModel.setAhCode(product.getAhCode());
       productResponseModel.setMchCode(product.getMchCode());
       productResponseModel.setBrandCode(product.getBrandCode());
       productResponseModel.setVariantData(JsonUtil.getMapFromJson(product.getVariantData()));
        return productResponseModel;
    }



}
