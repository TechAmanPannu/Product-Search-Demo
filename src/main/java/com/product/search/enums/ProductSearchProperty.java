
package com.product.search.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.EnumMap;
import java.util.Map;

public enum ProductSearchProperty {


    BRAND_CODE("brandCode", "products", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, "{tableName}.brand_code");
        put(ProductSearchOperator.CONTAINS, "{tableName}.brand_code");
    }}, new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, "=");
        put(ProductSearchOperator.CONTAINS, "=");
    }}),

    AH_CODE("ahCode", "products",  new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, "{tableName}.ah_code");
        put(ProductSearchOperator.CONTAINS, "{tableName}.ah_code");
    }},new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, "=");
        put(ProductSearchOperator.CONTAINS, "=");
    }}),
    MCH_CODE("mchCode", "products",  new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, "{tableName}.mch_code");
        put(ProductSearchOperator.CONTAINS, "{tableName}.mch_code");
    }},new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, "=");
        put(ProductSearchOperator.CONTAINS, "=");
    }}),

    PRODUCT_CATEGORY_NAME("productCategoryName", "categories", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, "{tableName}.name ->> 'en'");
        put(ProductSearchOperator.CONTAINS, "jsonb_to_tsvector('english', {tableName}.name -> CAST('en' AS TEXT), '[\"string\"]')");
    }}, new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, "=");
        put(ProductSearchOperator.CONTAINS, "@@");
    }}),


    DELIVERY("delivery", "products",  new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, "LOWER({tableName}.enrichment -> 'specifications' -> 'en' ->> 'delivery')");
        put(ProductSearchOperator.CONTAINS, "jsonb_to_tsvector('english', {tableName}.enrichment ->  CAST('specifications' AS TEXT) -> CAST('en' AS TEXT) -> CAST('delivery' AS TEXT), '[\"string\"]')");
    }},new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, "=");
        put(ProductSearchOperator.CONTAINS, "@@");
    }}, true),


    SKIN_CONCERN("skinConcern", "products",  new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, "LOWER({tableName}.enrichment -> 'specifications' -> 'code' ->> 'concern')");
        put(ProductSearchOperator.CONTAINS, "jsonb_to_tsvector('english', {tableName}.enrichment ->  CAST('specifications' AS TEXT) -> CAST('code' AS TEXT) -> CAST('concern' AS TEXT), '[\"string\"]')");
    }},new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, "=");
        put(ProductSearchOperator.CONTAINS, "@@");
    }}, true),


    PRODUCT_COLOR("productColor", "products",  new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, "LOWER({tableName}.variant_data -> 'color' ->> 'en')");
        put(ProductSearchOperator.CONTAINS, "jsonb_to_tsvector('english', {tableName}.variant_data -> CAST('color' AS TEXT) -> CAST('en' AS TEXT), '[\"string\"]')");
    }},new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, "=");
        put(ProductSearchOperator.CONTAINS, "@@");
    }}, true),


//    Search query is using only operator mapping from dietary configuration, others are  statically defined in the SubQueryBuilder,
//    It was done after seeing the complexity of the query required.
    DIETARY("dietary", "products",  new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, "{tableName}.enrichment -> 'dietary_callouts'");
        put(ProductSearchOperator.CONTAINS, "{tableName}.enrichment -> 'dietary_callouts'");
    }},new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, "@@");
        put(ProductSearchOperator.CONTAINS, "@@");
    }});


    private String value;
    private String tableName;
    private Map<ProductSearchOperator, String> searchOperatorMappings;
    private Map<ProductSearchOperator, String> searchOperatorsColumnMappings;
    private boolean lowerIndexCreated;


    ProductSearchProperty(String value, String tableName, Map<ProductSearchOperator, String> searchOperatorsColumnMappings, Map<ProductSearchOperator, String> searchOperatorMappings) {
        this(value, tableName, searchOperatorsColumnMappings,searchOperatorMappings, false);

    }
    ProductSearchProperty(String value, String tableName, Map<ProductSearchOperator, String> searchOperatorsColumnMappings, Map<ProductSearchOperator, String> searchOperatorMappings, boolean lowerIndexCreated) {
        this.value = value;
        this.tableName = tableName;
        this.searchOperatorsColumnMappings = searchOperatorsColumnMappings;
        this.searchOperatorMappings = searchOperatorMappings;
        this.lowerIndexCreated = lowerIndexCreated;

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


    public String getColumnName(ProductSearchOperator operator, boolean isJoinQuery) {
        String column = this.searchOperatorsColumnMappings.get(operator);
        if (isJoinQuery) {
          return column.replaceFirst("\\{tableName}", this.tableName);
        } else {
            return column.replaceFirst("\\{tableName}.", "");
        }
    }

    public String getColumnName(ProductSearchOperator operator) {
        return getColumnName(operator, false);
    }

    public String getOperator(ProductSearchOperator productSearchOperator) {
        return this.searchOperatorMappings.get(productSearchOperator);
    }

    public boolean isLowerIndexCreated() {
        return this.lowerIndexCreated;
    }
}

