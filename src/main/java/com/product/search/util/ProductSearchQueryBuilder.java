/*
package com.product.search.util;

import com.product.search.model.request.ProductSearchCondition;
import com.product.search.model.request.ProductSearchRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ProductSearchQueryBuilder {

    private ProductSearchRequest productSearchRequest;

    private static String LIMIT_PATTERN = "@_limit_@";
    private static String NEXT_PAGE_PATTERN = "@_nextpage_@";

    private static String AND_CONDITIONS_PATTERN = "@_and_@";

    private static String OR_CONDITIONS_PATTERN= "@_or_@";

    private static String STRING_EXPRESSION_PATTERN = " %s %s '%s' ";

    private static String QUERY_INITIAL_SELECT = "SELECT id, liam FROM ";

    private static String BASIC_AND_QUERY = QUERY_INITIAL_SELECT + "products WHERE id > @_nextpage_@ AND @_and_@ LIMIT @_limit_@";

    private static String SUBQUERY_AND_QUERY = QUERY_INITIAL_SELECT + "";

    private static String BASIC_OR_QUERY = QUERY_INITIAL_SELECT + "products WHERE id > @_nextpage_@ AND @_or_@ LIMIT @_limit_@";

    private ProductSearchQueryBuilder(ProductSearchRequest productSearchRequest) {
        this.productSearchRequest = productSearchRequest;
    }

    public static ProductSearchQueryBuilder of(ProductSearchRequest productSearchRequest) {
        return new ProductSearchQueryBuilder(productSearchRequest);
    }

    public String  build(int limit, long nextPage) {
        if(productSearchRequest == null) {
            throw new IllegalArgumentException("Invalid product search request to build the query");
        }

        String conditions = null;
        if(productSearchRequest.isAllConditionsMatch()) {
            conditions = getAndConditions();
            return BASIC_AND_QUERY.replace(AND_CONDITIONS_PATTERN, conditions.toString())
                    .replace(NEXT_PAGE_PATTERN, "0")
                    .replace(LIMIT_PATTERN, "50");
        } else {
            conditions = getOrConditions();
            return BASIC_OR_QUERY.replace(OR_CONDITIONS_PATTERN, conditions.toString())
                    .replace(NEXT_PAGE_PATTERN, )
                    .replace(LIMIT_PATTERN, "50");
        }
    }


    private String getAndConditions() {
        StringJoiner stringJoiner = new StringJoiner("AND");
        for (ProductSearchCondition searchCondition : productSearchRequest.getConditions()) {
            if(searchCondition == null) {
                continue;
            }
            stringJoiner.add(String.format(STRING_EXPRESSION_PATTERN, searchCondition.getProperty(), searchCondition.getOperator(), searchCondition.getValue()));

        }
        return stringJoiner.toString();
    }

    private String getOrConditions() {
        StringJoiner stringJoiner = new StringJoiner("OR");
        for (ProductSearchCondition searchCondition : productSearchRequest.getConditions()) {
            if(searchCondition == null) {
                continue;
            }
            stringJoiner.add(String.format(STRING_EXPRESSION_PATTERN, searchCondition.getProperty(), searchCondition.getOperator(), searchCondition.getValue()));

        }
        return stringJoiner.toString();
    }
}
*/
