package com.product.search;

import com.product.search.util.query.QueryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


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

}
