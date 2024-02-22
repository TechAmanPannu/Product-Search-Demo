package com.product.search;

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
        String query = new QueryBuilder("products", "id", "liam")
                .where("ah_code", "=", singleQuote("240769"))
                .and("mch_code", "=", singleQuote("354345435"))
                .or("id", "=", singleQuote("234234234"))
                .build().sortBy("id", "ASC").limit("50").get();


        Assertions.assertEquals("SELECT id,liam FROM products WHERE  ( ah_code = '240769' AND mch_code = '354345435' OR id = '234234234'  ) ORDER BY id ASC LIMIT 50", query);


        String query_1 = new QueryBuilder("products", "id", "liam")
                .where("id", ">", "0")
                .and("mch_code", "=", List.of(singleQuote("354345435"), singleQuote("000000"), singleQuote("1111111")))
                .and("brand_code", "=", List.of(singleQuote("6666666"), singleQuote("777777"), singleQuote("66666666")))
                .build().sortBy("id", "ASC").limit("50").get();


        Assertions.assertEquals("SELECT id,liam FROM products WHERE  ( id > 0 AND  ( mch_code = '354345435' OR mch_code = '000000' OR mch_code = '1111111'  ) AND  ( brand_code = '6666666' OR brand_code = '777777' OR brand_code = '66666666'  )  ) ORDER BY id ASC LIMIT 50", query_1);


        String query_2 = new QueryBuilder("products", "id", "liam")
                .where("id", ">", "0")
                .and("mch_code", "=", List.of(singleQuote("354345435"), singleQuote("000000"), singleQuote("1111111")))
                .and("brand_code", "=", singleQuote("9999999"))
                .build().sortBy("id", "ASC").limit("50").get();


        Assertions.assertEquals("SELECT id,liam FROM products WHERE  ( id > 0 AND  ( mch_code = '354345435' OR mch_code = '000000' OR mch_code = '1111111'  ) AND brand_code = '9999999'  ) ORDER BY id ASC LIMIT 50", query_2);

        // subquery query with order by
        String subquery = new QueryBuilder("products", "id", "liam")
                .subquery()
                .where("ah_code", "=", singleQuote("20348304"))
                .and("mch_code", "=", singleQuote("354345435"))
                .or("id", "=", singleQuote("234234234"))
                .build().sortBy("id", "ASC").offset("0").build().get();


        Assertions.assertEquals("SELECT id,liam FROM  ( SELECT id,liam FROM products WHERE  ( ah_code = '20348304' AND mch_code = '354345435' OR id = '234234234'  ) ORDER BY id ASC OFFSET 0 ) AS subquery", subquery);

// subquery query outside orderby
        String subquery_with_outside_orderBy = new QueryBuilder("products", "id", "liam")
                .subquery()
                .where("ah_code", "=", singleQuote("20348304"))
                .and("mch_code", "=", singleQuote("354345435"))
                .or("id", "=", singleQuote("234234234"))
                .build().build().sortBy("id", "ASC").get();

        Assertions.assertEquals("SELECT id,liam FROM  ( SELECT id,liam FROM products WHERE  ( ah_code = '20348304' AND mch_code = '354345435' OR id = '234234234'  ) ) AS subquery ORDER BY id ASC", subquery_with_outside_orderBy);

//        join query
        String joinQuery = new QueryBuilder("products", "id", "liam")
                .joinSubquery("categories", " products.category_id = categories.id ", "products.id", "products.liam")
                .where("products.id", ">", "0")
                .and("categories.name ->> 'en'", "=", singleQuote("Vitamin C"))
                .build()
                .offset("0")
                .build().sortBy("id", "ASC").limit("50").get();

        Assertions.assertEquals("SELECT id,liam FROM  ( SELECT products.id,products.liam FROM products JOIN categories ON  products.category_id = categories.id WHERE  ( products.id > 0 AND categories.name ->> 'en' = 'Vitamin C'  ) OFFSET 0 ) AS subquery ORDER BY subquery.id ASC LIMIT 50", joinQuery);


        // subquery query outside orderby
        String subquery_dietary = new QueryBuilder("products", "id", "liam")
                .subquery()
                .where("id", ">", "0")
                .and("jsonb_to_tsvector('english', enrichment -> 'dietary_callouts', '[\"string\"]')", "@@", "to_tsquery('sfsfdsdf')")
                .or("((enrichment -> 'dietary_callouts' = '[]' OR enrichment -> 'dietary_callouts'= 'null')")
                .and("jsonb_to_tsvector('english', data -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('sfsfdsdf') )")
                .build().offset("0").build().sortBy("id", "ASC").limit("50").get();

        Assertions.assertEquals("SELECT id,liam FROM  ( SELECT id,liam FROM products WHERE  ( id > 0 AND jsonb_to_tsvector('english', enrichment -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('sfsfdsdf') OR ((enrichment -> 'dietary_callouts' = '[]' OR enrichment -> 'dietary_callouts'= 'null')AND jsonb_to_tsvector('english', data -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('sfsfdsdf') ) ) OFFSET 0 ) AS subquery ORDER BY id ASC LIMIT 50", subquery_dietary);

        String subquery_dietary_nextPage = new QueryBuilder("products", "id", "liam")
                .subquery()
                .where("mch_code", "=", singleQuote("977677788"))
                .andDietary("@@", List.of("peanut"))
                .and("brand_code", "=", singleQuote("78766876"))
                .build().nextPage("id", "0").offset("0").build().sortBy("id", "ASC").limit("50").get();

        Assertions.assertEquals("SELECT id,liam FROM  ( SELECT id,liam FROM products WHERE id > 0  AND ( mch_code = '977677788' AND (jsonb_to_tsvector('english', enrichment -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('peanut') OR ((enrichment -> 'dietary_callouts' = '[]' OR enrichment -> 'dietary_callouts'= 'null') AND jsonb_to_tsvector('english', data -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('peanut') ))AND brand_code = '78766876'  ) OFFSET 0 ) AS subquery ORDER BY id ASC LIMIT 50", subquery_dietary_nextPage);


    }

    @Test
    public void test() {


        ProductSearchRequest productSearchRequest = constructSearchRequest(true);


        boolean isSubQueryNeeded = isSubqueryNeeded(productSearchRequest);


        List<ProductSearchCondition> conditions = productSearchRequest.getConditions();


        QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam", "ah_code", "mch_code", "brand_code", "enrichment", "data");
        String query = isSubQueryNeeded ? getSubQuery(productSearchRequest, queryBuilder)
                : getQuery(productSearchRequest, queryBuilder);

        Assertions.assertEquals("SELECT id,liam FROM  ( SELECT id,liam FROM products WHERE id > 0 AND ah_code = '234324' AND jsonb_to_tsvector('english', enrichment -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('peanut|butter') OR ((enrichment -> 'dietary_callouts' = '[]' OR enrichment -> 'dietary_callouts'= 'null') AND jsonb_to_tsvector('english', data -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('peanut|butter') )AND brand_code = '234324'  OFFSET 0 ) AS subquery ORDER BY id ASC", query);
    }

    private String getQuery(ProductSearchRequest productSearchRequest, QueryBuilder queryBuilder) {
        List<ProductSearchCondition> conditions = productSearchRequest.getConditions();
        WhereClause whereClause = queryBuilder.where(conditions.get(0).getProperty(), conditions.get(0).getOperator(), singleQuote(conditions.get(0).getValue()));
        whereClause = productSearchRequest.isAllConditionsMatch() ? getAllConditionsMatched(whereClause, conditions) : getAllConditionsNonMatched(whereClause, conditions);
        return whereClause.build().nextPage("id", "0").sortBy("id", "ASC").limit("50").get();
    }

    private String getSubQuery(ProductSearchRequest productSearchRequest, QueryBuilder queryBuilder) {

        List<ProductSearchCondition> conditions = productSearchRequest.getConditions();
        SubqueryWhereClause whereClause = queryBuilder.subquery().where(conditions.get(0).getProperty(), conditions.get(0).getOperator(), singleQuote(conditions.get(0).getValue()));
        whereClause = productSearchRequest.isAllConditionsMatch() ? getAllConditionsMatched(whereClause, conditions) : getAllConditionsNonMatched(whereClause, conditions);
        return whereClause.build().nextPage("id", "0").sortBy("id", "ASC").build().limit("50").get();
    }

    private SubqueryWhereClause getAllConditionsMatched(SubqueryWhereClause whereClause, List<ProductSearchCondition> conditions) {
        System.out.println(conditions);
        for (int i = 1; i < conditions.size(); i++) {
            if (conditions.get(i).getProperty().equals("dietary")) {
                whereClause = whereClause.andDietary("@@", List.of(conditions.get(i).getValue()));
            } else {
                whereClause = whereClause.and(conditions.get(i).getProperty(), conditions.get(i).getOperator(), singleQuote(conditions.get(i).getValue()));
            }
        }
        return whereClause;
    }

    private SubqueryWhereClause getAllConditionsNonMatched(SubqueryWhereClause whereClause, List<ProductSearchCondition> conditions) {
        for (int i = 1; i < conditions.size(); i++) {
            if (conditions.get(i).getProperty().equals("dietary")) {
                whereClause = whereClause.orDietary("@@", List.of(conditions.get(i).getValue()));
            } else {
                whereClause = whereClause.or(conditions.get(i).getProperty(), conditions.get(i).getOperator(), singleQuote(conditions.get(i).getValue()));
            }
        }

        return whereClause;
    }

    private WhereClause getAllConditionsMatched(WhereClause whereClause, List<ProductSearchCondition> conditions) {
        for (int i = 1; i < conditions.size(); i++) {
            whereClause = whereClause.and(conditions.get(i).getProperty(), conditions.get(i).getOperator(), singleQuote(conditions.get(i).getValue()));
        }
        return whereClause;
    }

    private WhereClause getAllConditionsNonMatched(WhereClause whereClause, List<ProductSearchCondition> conditions) {
        for (int i = 1; i < conditions.size(); i++) {
            whereClause = whereClause.or(conditions.get(i).getProperty(), conditions.get(i).getOperator(), singleQuote(conditions.get(i).getValue()));
        }
        return whereClause;
    }

    private boolean isSubqueryNeeded(ProductSearchRequest productSearchRequest) {
        for (ProductSearchCondition condition : productSearchRequest.getConditions()) {
            if (condition.getProperty().equals("dietary") || condition.getProperty().equals("skinConcern")) {
                return true;
            }
        }

        return false;
    }

    private ProductSearchRequest constructSearchRequest(boolean allConditionsMatch) {

        ProductSearchRequest productSearchRequest = new ProductSearchRequest();

        ProductSearchCondition productSearchCondition = new ProductSearchCondition();
        productSearchCondition.setProperty("ah_code");
        productSearchCondition.setOperator("=");
        productSearchCondition.setValue("240434");

        ProductSearchCondition productSearchCondition_1 = new ProductSearchCondition();
        productSearchCondition_1.setProperty("mch_code");
        productSearchCondition_1.setOperator("=");
        productSearchCondition_1.setValue("M10210301");

        ProductSearchCondition productSearchCondition_2 = new ProductSearchCondition();
        productSearchCondition_2.setProperty("brand_code");
        productSearchCondition_2.setOperator("=");
        productSearchCondition_2.setValue("AMP");

        ProductSearchCondition productSearchCondition_3 = new ProductSearchCondition();
        productSearchCondition_3.setProperty("dietary");
        productSearchCondition_3.setOperator("CONTAINS");
        productSearchCondition_3.setValue("peanut");

        ProductSearchCondition productSearchCondition_4 = new ProductSearchCondition();
        productSearchCondition_4.setProperty("dietary");
        productSearchCondition_4.setOperator("CONTAINS");
        productSearchCondition_4.setValue("organic");

        productSearchRequest.setConditions(List.of(productSearchCondition, productSearchCondition_1, productSearchCondition_2, productSearchCondition_3, productSearchCondition_4));

        productSearchRequest.setAllConditionsMatch(allConditionsMatch);
        return productSearchRequest;
    }


    @Test
    public void test1() {

    }

}
