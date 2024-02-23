
package com.product.search.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.EnumMap;
import java.util.HashMap;

import java.util.Map;

public enum ProductSearchProperty {


    BRAND_CODE("brandCode", "products", "brand_code", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, "=");
        put(ProductSearchOperator.CONTAINS, "=");
    }}),

    AH_CODE("ahCode", "products", "ah_code", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, "=");
        put(ProductSearchOperator.CONTAINS, "=");
    }}),
    MCH_CODE("mchCode", "products", "mch_code", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, "=");
        put(ProductSearchOperator.CONTAINS, "=");
    }}),
    PRODUCT_COLOR("productColor", "products", "variant_data -> 'color' ->> 'en'", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, "=");
        put(ProductSearchOperator.CONTAINS, "=");
    }}),
    PRODUCT_CATEGORY_NAME("ProductCategoryName", "categories", "name ->> 'en'", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, "=");
        put(ProductSearchOperator.CONTAINS, "=");
    }}),

    DIETARY("dietary", "products", "enrichment -> 'dietary_callouts'", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, "=");
        put(ProductSearchOperator.CONTAINS, "@@");
    }}),

    SKIN_CONCERN("skinConcern", "products", "enrichment -> 'specifications'::text -> 'code'::text -> 'concern'::text", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, "=");
        put(ProductSearchOperator.CONTAINS, "@@");
    }});


    private String value;
    private String tableName;
    private String columnName;
    private Map<ProductSearchOperator, String> searchOperatorMappings;

    private ProductSearchQueryType queryType;


    ProductSearchProperty(String value, String tableName, String columnName, Map<ProductSearchOperator, String> searchOperatorMappings) {
        this.value = value;
        this.tableName = tableName;
        this.columnName = columnName;
        this.searchOperatorMappings = searchOperatorMappings;

    }

    @JsonCreator
    public static ProductSearchProperty from(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        for (ProductSearchProperty productSearchProperty : ProductSearchProperty.values()) {
            if (productSearchProperty.value.equalsIgnoreCase(value)) {
                return productSearchProperty;
            }
        }
        return null;
    }


    public String getColumnName(boolean isJoinQuery) {
        if (!isJoinQuery) {
          return this.getColumnName();
        }
        return String.format("%s.%s", this.tableName, this.columnName);
    }

    public String getColumnName() {
        return this.columnName;
    }

    public String getOperator(ProductSearchOperator productSearchOperator) {
        return this.searchOperatorMappings.get(productSearchOperator);
    }

}

