
package com.product.search.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.product.search.model.ProductSearchOperatorConfiguration;
import com.product.search.model.request.ProductSearchCondition;

import java.util.*;

import static com.product.search.util.query.QueryUtils.join;

public enum ProductSearchProperty {


    BRAND_CODE("brandCode", "products", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("{tname}.brand_code", "=", "'{val}'", ProductSearchQueryType.BASIC_QUERY));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("{tname}.brand_code", "=", "'{val}'", ProductSearchQueryType.BASIC_QUERY));
    }}),

    AH_CODE("ahCode", "products", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("{tname}.ah_code", "=", "'{val}'", ProductSearchQueryType.BASIC_QUERY));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("{tname}.ah_code", "=", "'{val}'", ProductSearchQueryType.BASIC_QUERY));
    }}),
    MCH_CODE("mchCode", "products", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("{tname}.mch_code", "=", "'{val}'", ProductSearchQueryType.BASIC_QUERY));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("{tname}.mch_code", "=", "'{val}'", ProductSearchQueryType.BASIC_QUERY));
    }}),

    PRODUCT_CATEGORY_NAME("productCategoryName", "categories", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("{tname}.name ->> 'en'", "=", "'{val}'", ProductSearchQueryType.JOIN_QUERY));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("jsonb_to_tsvector('english', {tname}.name -> CAST('en' AS TEXT), '[\"string\"]')", "@@", "to_tsquery('{val}')", false, true, ProductSearchQueryType.JOIN_QUERY));
    }}),


    DELIVERY("delivery", "products", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("LOWER({tname}.enrichment -> 'specifications' -> 'en' ->> 'delivery')", "=", "'{val}'", true, ProductSearchQueryType.SUB_QUERY));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("jsonb_to_tsvector('english', {tname}.enrichment ->  CAST('specifications' AS TEXT) -> CAST('en' AS TEXT) -> CAST('delivery' AS TEXT), '[\"string\"]')", "@@", "to_tsquery('{val}')", false, true, ProductSearchQueryType.SUB_QUERY));
    }}),


    SKIN_CONCERN("skinConcern", "products", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("LOWER({tname}.enrichment -> 'specifications' -> 'code' ->> 'concern')", "=", "'{val}'", true, ProductSearchQueryType.SUB_QUERY));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("jsonb_to_tsvector('english', {tname}.enrichment ->  CAST('specifications' AS TEXT) -> CAST('code' AS TEXT) -> CAST('concern' AS TEXT), '[\"string\"]')", "@@", "to_tsquery('{val}')", false, true, ProductSearchQueryType.SUB_QUERY));
    }}),


    PRODUCT_COLOR("productColor", "products", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("LOWER({tname}.variant_data -> 'color' ->> 'en')", "=", "'{val}'", true, ProductSearchQueryType.BASIC_QUERY));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("jsonb_to_tsvector('english', {tname}.variant_data -> CAST('color' AS TEXT) -> CAST('en' AS TEXT), '[\"string\"]')", "@@", "to_tsquery('{val}')", false, true, ProductSearchQueryType.SUB_QUERY));
    }}),

    LIAM("liam", "products", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("{tname}.liam", "=", "'{val}'", ProductSearchQueryType.BASIC_QUERY));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("{tname}.liam", "LIKE", "'%{val}%'", ProductSearchQueryType.BASIC_QUERY));
    }}),


    //    Search query is using only operator mapping from dietary configuration, others are  statically defined in the SubQueryBuilder,
//    It was done after seeing the complexity of the query required.
    DIETARY("dietary", "products", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("@@", ProductSearchQueryType.SUB_QUERY));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("@@", ProductSearchQueryType.SUB_QUERY));
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

    public static ProductSearchQueryType getSearchQueryType(List<ProductSearchCondition> productSearchConditions) {
        if(productSearchConditions == null || productSearchConditions.isEmpty()) {
            return ProductSearchQueryType.BASIC_QUERY;
        }
        Set<ProductSearchQueryType> eligibleQueryTypes = new HashSet<>();
        eligibleQueryTypes.add(ProductSearchQueryType.BASIC_QUERY);

        for (ProductSearchCondition productSearchCondition : productSearchConditions) {
            eligibleQueryTypes.add(productSearchCondition.getProperty().getRequiredQueryType(productSearchCondition.getOperator()));
        }

        return ProductSearchQueryType.findHighPriorityQueryType(new ArrayList<>(eligibleQueryTypes));

    }

    private ProductSearchQueryType getRequiredQueryType(ProductSearchOperator operator) {
        return this.operatorConfigurations.get(operator).getRequiredQueryType();
    }
}

