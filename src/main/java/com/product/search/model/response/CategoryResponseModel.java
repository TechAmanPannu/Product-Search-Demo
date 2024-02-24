package com.product.search.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CategoryResponseModel {

    private Long id;
    private String name;
    private String type;
}
