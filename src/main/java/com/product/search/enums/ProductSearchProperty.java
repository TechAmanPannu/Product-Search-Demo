
package com.product.search.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.product.search.model.ProductSearchOperatorConfiguration;
import com.product.search.model.request.ProductSearchCondition;

import java.util.*;
import java.util.stream.Collectors;

import static com.product.search.util.query.QueryUtils.join;

public enum ProductSearchProperty {


    BRAND_CODE("brandCode", "products", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("{tname}.brand_code", "=", "'{val}'", ProductSearchQueryType.BASIC_QUERY, 1));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("{tname}.brand_code", "=", "'{val}'", ProductSearchQueryType.BASIC_QUERY, 1));
    }}),

    AH_CODE("ahCode", "products", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("{tname}.ah_code", "=", "'{val}'", ProductSearchQueryType.BASIC_QUERY, 1));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("{tname}.ah_code", "=", "'{val}'", ProductSearchQueryType.BASIC_QUERY, 1));
    }}),
    MCH_CODE("mchCode", "products", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("{tname}.mch_code", "=", "'{val}'", ProductSearchQueryType.BASIC_QUERY, 1));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("{tname}.mch_code", "=", "'{val}'", ProductSearchQueryType.BASIC_QUERY, 1));
    }}),

    PRODUCT_CATEGORY_NAME("productCategoryName", "categories", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("{tname}.name ->> 'en'", "=", "'{val}'", ProductSearchQueryType.JOIN_QUERY, 3));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("jsonb_to_tsvector('english', {tname}.name -> CAST('en' AS TEXT), '[\"string\"]')", "@@", "to_tsquery('{val}')", false, true, ProductSearchQueryType.JOIN_QUERY, 3));
    }}),


    DELIVERY("delivery", "products", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("LOWER({tname}.enrichment -> 'specifications' -> 'en' ->> 'delivery')", "=", "'{val}'", true, ProductSearchQueryType.SUB_QUERY, 2));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("jsonb_to_tsvector('english', {tname}.enrichment ->  CAST('specifications' AS TEXT) -> CAST('en' AS TEXT) -> CAST('delivery' AS TEXT), '[\"string\"]')", "@@", "to_tsquery('{val}')", false, true, ProductSearchQueryType.SUB_QUERY, 2));
    }}),


    SKIN_CONCERN("skinConcern", "products", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("LOWER({tname}.enrichment -> 'specifications' -> 'code' ->> 'concern')", "=", "'{val}'", true, ProductSearchQueryType.SUB_QUERY, 2));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("jsonb_to_tsvector('english', {tname}.enrichment ->  CAST('specifications' AS TEXT) -> CAST('code' AS TEXT) -> CAST('concern' AS TEXT), '[\"string\"]')", "@@", "to_tsquery('{val}')", false, true, ProductSearchQueryType.SUB_QUERY, 2));
    }}),


    PRODUCT_COLOR("productColor", "products", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("LOWER({tname}.variant_data -> 'color' ->> 'en')", "=", "'{val}'", true, ProductSearchQueryType.BASIC_QUERY, 1));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("jsonb_to_tsvector('english', {tname}.variant_data -> CAST('color' AS TEXT) -> CAST('en' AS TEXT), '[\"string\"]')", "@@", "to_tsquery('{val}')", false, true, ProductSearchQueryType.SUB_QUERY, 2));
    }}),

    LIAM("liam", "products", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("{tname}.liam", "=", "'{val}'", ProductSearchQueryType.BASIC_QUERY, 0));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("{tname}.liam", "ILIKE", "'%{val}%'", ProductSearchQueryType.BASIC_QUERY, 2));
    }}),


    //    Search query is using only operator mapping from dietary configuration, others are  statically defined in the SubQueryBuilder,
//    It was done after seeing the complexity of the query required.
    DIETARY("dietary", "products", new EnumMap<>(ProductSearchOperator.class) {{
        put(ProductSearchOperator.EQ, new ProductSearchOperatorConfiguration("@@", false, true, ProductSearchQueryType.SUB_QUERY, 2));
        put(ProductSearchOperator.CONTAINS, new ProductSearchOperatorConfiguration("@@", false, true, ProductSearchQueryType.SUB_QUERY, 2));
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

    public static List<ProductSearchCondition> sortByOrderPriority(List<ProductSearchCondition> conditions) {
        return conditions.stream()
                .sorted(Comparator.comparing(condition -> condition.getProperty().getOrderPriority(condition.getOperator())))
                .collect(Collectors.toList());
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

    public List<String> getValue(ProductSearchOperator searchOperator, List<String> requestedValues) {

        ProductSearchOperatorConfiguration operatorConfiguration = this.operatorConfigurations.get(searchOperator);
        String valueExpression = operatorConfiguration.getValueExpression();

        List<String> updatedValues = new ArrayList<>();
        for (String requestedValue : requestedValues) {
            if (operatorConfiguration.isLowerIndex()) {
                requestedValue = requestedValue.toLowerCase();
            }
            if (operatorConfiguration.isTsQuery()) {
                requestedValue = join("&", requestedValue.split(" "));
            }

            if (valueExpression != null && !valueExpression.isEmpty()) {
                requestedValue = valueExpression.replaceFirst("\\{val}", requestedValue);
            }
            updatedValues.add(requestedValue);
        }
        return updatedValues;
    }

    public static ProductSearchQueryType getSearchQueryType(List<ProductSearchCondition> productSearchConditions) {
        if (productSearchConditions == null || productSearchConditions.isEmpty()) {
            return ProductSearchQueryType.BASIC_QUERY;
        }
        Set<ProductSearchQueryType> eligibleQueryTypes = new HashSet<>();
        eligibleQueryTypes.add(ProductSearchQueryType.BASIC_QUERY);

        for (ProductSearchCondition productSearchCondition : productSearchConditions) {
            eligibleQueryTypes.add(productSearchCondition.getProperty().getRequiredQueryType(productSearchCondition.getOperator()));
        }

        return ProductSearchQueryType.findTopRankedQueryType(new ArrayList<>(eligibleQueryTypes));

    }

    public int getOrderPriority(ProductSearchOperator productSearchOperator) {
        return this.operatorConfigurations.get(productSearchOperator).getOrderPriority();
    }

    private ProductSearchQueryType getRequiredQueryType(ProductSearchOperator operator) {
        return this.operatorConfigurations.get(operator).getRequiredQueryType();
    }
}

