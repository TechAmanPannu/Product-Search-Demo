package com.product.search;

import com.product.search.enums.ProductSearchOperator;
import com.product.search.enums.ProductSearchProperty;
import com.product.search.model.request.ProductSearchCondition;
import com.product.search.model.request.ProductSearchRequest;
import com.product.search.util.query.QueryBuilder;
import com.product.search.util.query.SubqueryBuilder;
import com.product.search.util.query.SubqueryWhereClause;
import com.product.search.util.query.WhereClause;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.product.search.util.query.QueryUtils.singleQuote;


class DemoProductSearchApplicationTests {

    @Test
    void contextLoads() {


        // basic query
    }

    @Test
    public void test() {


//        ProductSearchRequest productSearchRequest = constructSearchRequest(true);
//
//
//        boolean isSubQueryNeeded = isSubqueryNeeded(productSearchRequest);
//        boolean isJoinNeeded = isJoinNeeded(productSearchRequest);
//        String query = null;
//        QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam");
//
//        if (isJoinNeeded) {
//            query = getJoinQuery(productSearchRequest, queryBuilder);
//        } else if (isSubQueryNeeded) {
//            query = getSubQuery(productSearchRequest, queryBuilder);
//        } else {
//            query = getQuery(productSearchRequest, queryBuilder);
//        }
//
//
//        Assertions.assertEquals("SELECT id,liam FROM  ( SELECT id,liam FROM products WHERE id > 0 AND ah_code = '234324' AND jsonb_to_tsvector('english', enrichment -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('peanut|butter') OR ((enrichment -> 'dietary_callouts' = '[]' OR enrichment -> 'dietary_callouts'= 'null') AND jsonb_to_tsvector('english', data -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('peanut|butter') )AND brand_code = '234324'  OFFSET 0 ) AS subquery ORDER BY id ASC", query);
//    }
//
//    private String getJoinQuery(ProductSearchRequest productSearchRequest, QueryBuilder queryBuilder) {
//        List<ProductSearchCondition> conditions = productSearchRequest.getConditions();
//        SubqueryWhereClause whereClause = queryBuilder.joinSubquery("categories", "products.category_id = categories.id","products.id", "products.liam").where(conditions.get(0).getProperty().getColumnName(), conditions.get(0).getProperty().getOperator(conditions.get(0).getOperator()), singleQuote(conditions.get(0).getValue()));
//        whereClause = productSearchRequest.isAllConditionsMatch() ? getAllConditionsMatched(whereClause, conditions, true) : getAllConditionsNonMatched(whereClause, conditions, true);
//        return whereClause.build().offset("0").nextPage("products.id", "0").build().sortBy("id", "ASC").limit("50").get();
//    }
//
//
//    private String getQuery(ProductSearchRequest productSearchRequest, QueryBuilder queryBuilder) {
//        List<ProductSearchCondition> conditions = productSearchRequest.getConditions();
//        WhereClause whereClause = queryBuilder.where(conditions.get(0).getProperty().getColumnName(), conditions.get(0).getProperty().getOperator(conditions.get(0).getOperator()), singleQuote(conditions.get(0).getValue()));
//        whereClause = productSearchRequest.isAllConditionsMatch() ? getAllConditionsMatched(whereClause, conditions) : getAllConditionsNonMatched(whereClause, conditions);
//        return whereClause.build().nextPage("id", "0").sortBy("id", "ASC").limit("50").get();
//    }
//
//    private String getSubQuery(ProductSearchRequest productSearchRequest, QueryBuilder queryBuilder) {
//
//        List<ProductSearchCondition> conditions = productSearchRequest.getConditions();
//        SubqueryWhereClause whereClause = queryBuilder.subquery().where(conditions.get(0).getProperty().getColumnName(), conditions.get(0).getProperty().getOperator(conditions.get(0).getOperator()), singleQuote(conditions.get(0).getValue()));
//        whereClause = productSearchRequest.isAllConditionsMatch() ? getAllConditionsMatched(whereClause, conditions) : getAllConditionsNonMatched(whereClause, conditions);
//        return whereClause.build().offset("0").nextPage("id", "0").build().sortBy("id", "ASC").limit("50").get();
//    }
//
//
//    private SubqueryWhereClause getAllConditionsMatched(SubqueryWhereClause whereClause, List<ProductSearchCondition> conditions) {
//        return getAllConditionsMatched(whereClause, conditions, false);
//    }
//
//    private SubqueryWhereClause getAllConditionsNonMatched(SubqueryWhereClause whereClause, List<ProductSearchCondition> conditions) {
//        return getAllConditionsNonMatched(whereClause, conditions, false);
//    }
//
//    private SubqueryWhereClause getAllConditionsMatched(SubqueryWhereClause whereClause, List<ProductSearchCondition> conditions, boolean isJoinQuery) {
//        for (int i = 0; i < conditions.size(); i++) {
//            if (conditions.get(i).getProperty() == ProductSearchProperty.DIETARY) {
//                whereClause = whereClause.andProductDietary(ProductSearchProperty.DIETARY.getOperator(conditions.get(i).getOperator()), List.of(conditions.get(i).getValue()));
//            } else {
//                whereClause = whereClause.and(conditions.get(i).getProperty().getColumnName(isJoinQuery), conditions.get(i).getProperty().getOperator(conditions.get(i).getOperator()), singleQuote(conditions.get(i).getValue()));
//            }
//        }
//        return whereClause;
//    }
//
//    private SubqueryWhereClause getAllConditionsNonMatched(SubqueryWhereClause whereClause, List<ProductSearchCondition> conditions, boolean isJoinedQuery) {
//        for (int i = 0; i < conditions.size(); i++) {
//            if (conditions.get(i).getProperty() == ProductSearchProperty.DIETARY) {
//                whereClause = whereClause.orProductDietary(ProductSearchProperty.DIETARY.getOperator(conditions.get(i).getOperator()), List.of(conditions.get(i).getValue()));
//            } else {
//                whereClause = whereClause.or(conditions.get(i).getProperty().getColumnName(isJoinedQuery), conditions.get(i).getProperty().getOperator(conditions.get(i).getOperator()), singleQuote(conditions.get(i).getValue()));
//            }
//        }
//
//        return whereClause;
//    }
//
//    private WhereClause getAllConditionsMatched(WhereClause whereClause, List<ProductSearchCondition> conditions) {
//        for (int i = 0; i < conditions.size(); i++) {
//            whereClause = whereClause.and(conditions.get(i).getProperty().getColumnName(), conditions.get(i).getProperty().getOperator(conditions.get(i).getOperator()), singleQuote(conditions.get(i).getValue()));
//        }
//        return whereClause;
//    }
//
//    private WhereClause getAllConditionsNonMatched(WhereClause whereClause, List<ProductSearchCondition> conditions) {
//        for (int i = 0; i < conditions.size(); i++) {
//            whereClause = whereClause.or(conditions.get(i).getProperty().getColumnName(), conditions.get(i).getProperty().getOperator(conditions.get(i).getOperator()), singleQuote(conditions.get(i).getValue()));
//        }
//        return whereClause;
//    }
//
//    private boolean isSubqueryNeeded(ProductSearchRequest productSearchRequest) {
//        for (ProductSearchCondition condition : productSearchRequest.getConditions()) {
//            if (condition.getProperty() == ProductSearchProperty.DIETARY || condition.getProperty() == ProductSearchProperty.SKIN_CONCERN) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    private boolean isJoinNeeded(ProductSearchRequest productSearchRequest) {
//        for (ProductSearchCondition condition : productSearchRequest.getConditions()) {
//            if (condition.getProperty() == ProductSearchProperty.PRODUCT_CATEGORY_NAME) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    private ProductSearchRequest constructSearchRequest(boolean allConditionsMatch) {
//
//        ProductSearchRequest productSearchRequest = new ProductSearchRequest();
//
//        ProductSearchCondition productSearchCondition = new ProductSearchCondition();
//        productSearchCondition.setProperty(ProductSearchProperty.AH_CODE);
//        productSearchCondition.setOperator(ProductSearchOperator.EQ);
//        productSearchCondition.setValue("240591");
//
//        ProductSearchCondition productSearchCondition_1 = new ProductSearchCondition();
//        productSearchCondition_1.setProperty(ProductSearchProperty.MCH_CODE);
//        productSearchCondition_1.setOperator(ProductSearchOperator.EQ);
//        productSearchCondition_1.setValue("M10210501");
//
//        ProductSearchCondition productSearchCondition_2 = new ProductSearchCondition();
//        productSearchCondition_2.setProperty(ProductSearchProperty.BRAND_CODE);
//        productSearchCondition_2.setOperator(ProductSearchOperator.EQ);
//        productSearchCondition_2.setValue("CHRE");
//
//        ProductSearchCondition productSearchCondition_3 = new ProductSearchCondition();
//        productSearchCondition_3.setProperty(ProductSearchProperty.DIETARY);
//        productSearchCondition_3.setOperator(ProductSearchOperator.CONTAINS);
//        productSearchCondition_3.setValue("peanut");
//
//        ProductSearchCondition productSearchCondition_4 = new ProductSearchCondition();
//        productSearchCondition_4.setProperty(ProductSearchProperty.PRODUCT_CATEGORY_NAME);
//        productSearchCondition_4.setOperator(ProductSearchOperator.CONTAINS);
//        productSearchCondition_4.setValue("Kids Cookies");
//
//
//        productSearchRequest.setConditions(List.of(productSearchCondition, productSearchCondition_1, productSearchCondition_2, productSearchCondition_3, productSearchCondition_4));
//
//        productSearchRequest.setAllConditionsMatch(allConditionsMatch);
//        return productSearchRequest;
//    }
//
//
//    @Test
//    public void test1() {
//
    }

}
