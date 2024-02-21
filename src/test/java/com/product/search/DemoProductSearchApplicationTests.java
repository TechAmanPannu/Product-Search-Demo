package com.product.search;

import com.product.search.model.request.ProductSearchCondition;
import com.product.search.model.request.ProductSearchRequest;
import com.product.search.util.query.QueryBuilder;
import com.product.search.util.query.WhereClause;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;


class DemoProductSearchApplicationTests {

    @Test
    void contextLoads() {


        // basic query
        String query = new QueryBuilder("products", "id", "liam")
                .where("ah_code", "=", "20348304")
                .and("mch_code", "=", "354345435")
                .or("id", "=", "234234234")
                .build().sortBy("id", "ASC").get();


        Assertions.assertEquals("SELECT id,liam FROM products WHERE ah_code = '20348304' AND mch_code = '354345435' OR id = '234234234'  ORDER BY id ASC", query);

        // subquery query with order by
        String subquery = new QueryBuilder("products", "id", "liam")
                .subquery("products", "id", "liam")
                .where("ah_code", "=", "20348304")
                .and("mch_code", "=", "354345435")
                .or("id", "=", "234234234")
                .build().sortBy("id", "ASC").build().get();




        Assertions.assertEquals("SELECT id,liam FROM  ( SELECT id,liam FROM products WHERE ah_code = '20348304' AND mch_code = '354345435' OR id = '234234234'  ORDER BY id ASC ) AS subquery", subquery);

// subquery query outside orderby
        String subquery_with_outside_orderBy = new QueryBuilder("products", "id", "liam")
                .subquery("products", "id", "liam")
                .where("ah_code", "=", "20348304")
                .and("mch_code", "=", "354345435")
                .or("id", "=", "234234234")
                .build().build().sortBy("id", "ASC").get();

        Assertions.assertEquals("SELECT id,liam FROM  ( SELECT id,liam FROM products WHERE ah_code = '20348304' AND mch_code = '354345435' OR id = '234234234'  ) AS subquery ORDER BY id ASC", subquery_with_outside_orderBy);

//        join query
        String joinQuery = new QueryBuilder("products", "id", "liam")
                .where("ah_code", "=", "20348304")
                .and("mch_code", "=", "354345435")
                .or("id", "=", "234234234")
                .build().get();

        Assertions.assertEquals("SELECT id,liam FROM products WHERE ah_code = '20348304' AND mch_code = '354345435' OR id = '234234234", joinQuery);

    }

    @Test
    public void test() {



        ProductSearchRequest productSearchRequest = new ProductSearchRequest();

        ProductSearchCondition productSearchCondition = new ProductSearchCondition();
        productSearchCondition.setProperty("ah_code");
        productSearchCondition.setOperator("=");
        productSearchCondition.setValue("234324");

        ProductSearchCondition productSearchCondition_1 = new ProductSearchCondition();
        productSearchCondition_1.setProperty("mch_code");
        productSearchCondition_1.setOperator("=");
        productSearchCondition_1.setValue("234324");

        ProductSearchCondition productSearchCondition_2 = new ProductSearchCondition();
        productSearchCondition_2.setProperty("brand_code");
        productSearchCondition_2.setOperator("=");
        productSearchCondition_2.setValue("234324");

        productSearchRequest.setConditions(List.of(productSearchCondition, productSearchCondition_1, productSearchCondition_2));

        productSearchRequest.setAllConditionsMatch(true);


        QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam");
        WhereClause where = queryBuilder.where("id", ">", "0");

        for (ProductSearchCondition productSearchConditionn : productSearchRequest.getConditions()) {
            where.and(productSearchConditionn.getProperty(), productSearchConditionn.getOperator(), productSearchConditionn.getValue());
        }

        String query = where.build().get();

        Assertions.assertEquals("asf", query);
    }

}
