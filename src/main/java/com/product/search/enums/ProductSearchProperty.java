
package com.product.search.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.product.search.model.ProductSearchOperatorConfiguration;

import java.util.EnumMap;
import java.util.Map;

import static com.product.search.util.query.QueryUtils.join;

public enum ProductSearchProperty {


    BRAND_CODE("brandCode", "products", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("{tname}.brand_code", "=", "'{val}'"));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("{tname}.brand_code", "=", "'{val}'"));
    }}),

    AH_CODE("ahCode", "products", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("{tname}.ah_code", "=", "'{val}'"));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("{tname}.ah_code", "=", "'{val}'"));
    }}),
    MCH_CODE("mchCode", "products", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("{tname}.mch_code", "=", "'{val}'"));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("{tname}.mch_code", "=", "'{val}'"));
    }}),

    PRODUCT_CATEGORY_NAME("productCategoryName", "categories", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("{tname}.name ->> 'en'", "=", "'{val}'"));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("jsonb_to_tsvector('english', {tname}.name -> CAST('en' AS TEXT), '[\"string\"]')", "@@", "to_tsquery('{val}')", false, true));
    }}),


    DELIVERY("delivery", "products", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("LOWER({tname}.enrichment -> 'specifications' -> 'en' ->> 'delivery')", "=", "'{val}'", true));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("jsonb_to_tsvector('english', {tname}.enrichment ->  CAST('specifications' AS TEXT) -> CAST('en' AS TEXT) -> CAST('delivery' AS TEXT), '[\"string\"]')", "@@", "to_tsquery('{val}')", false, true));
    }}),


    SKIN_CONCERN("skinConcern", "products", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("LOWER({tname}.enrichment -> 'specifications' -> 'code' ->> 'concern')", "=", "'{val}'", true));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("jsonb_to_tsvector('english', {tname}.enrichment ->  CAST('specifications' AS TEXT) -> CAST('code' AS TEXT) -> CAST('concern' AS TEXT), '[\"string\"]')", "@@", "to_tsquery('{val}')", false, true));
    }}),


    PRODUCT_COLOR("productColor", "products", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("LOWER({tname}.variant_data -> 'color' ->> 'en')", "=", "'{val}'", true));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("jsonb_to_tsvector('english', {tname}.variant_data -> CAST('color' AS TEXT) -> CAST('en' AS TEXT), '[\"string\"]')", "@@", "to_tsquery('{val}')", false, true));
    }}),

    LIAM("liam", "products", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("{tname}.liam", "=", "'{val}'"));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("{tname}.liam", "LIKE", "'%{val}%'"));
    }}),


    //    Search query is using only operator mapping from dietary configuration, others are  statically defined in the SubQueryBuilder,
//    It was done after seeing the complexity of the query required.
    DIETARY("dietary", "products", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("@@"));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("@@"));
    }});


    private String value;
    private String tableName;
    private Map<ProductSearchOperator, ProductSearchOperatorConfiguration> operatorConfigurations;

    ProductSearchProperty(String value, String tableName, Map<ProductSearchOperator, ProductSearchOperatorConfiguration> operatorConfigurations) {
        this.value = value;
        this.tableName = tableName;
        this.operatorConfigurations = operatorConfigurations;

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
        ProductSearchOperatorConfiguration configuration = this.operatorConfigurations.get(operator);
        if (isJoinQuery) {
            return configuration.getColumnExpression().replaceFirst("\\{tname}", this.tableName);
        } else {
            return configuration.getColumnExpression().replaceFirst("\\{tname}.", "");
        }
    }

    public String getColumnName(ProductSearchOperator operator) {
        return getColumnName(operator, false);
    }

    public String getOperator(ProductSearchOperator productSearchOperator) {
        return this.operatorConfigurations.get(productSearchOperator).getOperator();
    }

    public boolean isLowerIndexCreated(ProductSearchOperator productSearchOperator) {
        return this.operatorConfigurations.get(productSearchOperator).isLowerIndex();
    }

    public String getValue(ProductSearchOperator searchOperator, String requestedValue) {
        ProductSearchOperatorConfiguration productSearchOperator = this.operatorConfigurations.get(searchOperator);
        String valueExpression = productSearchOperator.getValueExpression();
        if (valueExpression == null || valueExpression.isEmpty()) {
            return requestedValue;
        }

        if (productSearchOperator.isLowerIndex()) {
            requestedValue = requestedValue.toLowerCase();
        }
        if (productSearchOperator.isTsQuery()) {
            requestedValue = join("&", requestedValue.split(" "));
        }
        return valueExpression.replaceFirst("\\{val}", requestedValue);
    }
}

