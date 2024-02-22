package com.product.search.util.query;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.product.search.util.query.QueryUtils.singleQuote;

public class QueryBuilderTests {


    @Test
    @DisplayName("Given : brand_code with EQ operator, When: query is generated with combination, Then: SQL native query should return")
    public void testBasicQuery0() {

        QueryBuilder queryBuilder = new QueryBuilder("products",  "brand_code")
                .where("brand_code", "=", singleQuote("AMP"))
                .build();

        Assertions.assertEquals("SELECT brand_code FROM products WHERE  ( brand_code = 'AMP'  )", queryBuilder.get());

    }

    @Test
    @DisplayName("Given : brand_code with EQ operator with multiple values, When: query is generated with combination, Then: SQL native query should return")
    public void testBasicQuery00() {

        QueryBuilder queryBuilder = new QueryBuilder("products", "brand_code")
                .where("brand_code", "=", List.of(singleQuote("AMP"), singleQuote("GTRD")))
                .build();

        Assertions.assertEquals("SELECT brand_code FROM products WHERE  (  ( brand_code = 'AMP' OR brand_code = 'GTRD'  )  )", queryBuilder.get());

    }

    @Test
    @DisplayName("Given : basic AND query with ah_code, mch_code and brand_code with EQ operator, When: query is generated with combination, Then: SQL native query should return")
    public void testBasicQuery1() {

        QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam", "ah_code", "mch_code", "brand_code")
                .where("ah_code", "=", singleQuote("240434"))
                .and("mch_code", "=", singleQuote("M10210301"))
                .and("brand_code", "=", singleQuote("AMP"))
                .build();

        Assertions.assertEquals("SELECT id,liam,ah_code,mch_code,brand_code FROM products WHERE  ( ah_code = '240434' AND mch_code = 'M10210301' AND brand_code = 'AMP'  )", queryBuilder.get());

    }


    @Test
    @DisplayName("Given : basic AND query with ah_code, mch_code and brand_code with EQ operator, nextpage, order by and limit, When: query is generated with combination, Then: SQL native query should return")
    public void testBasicQuery2() {

        QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam", "ah_code", "mch_code", "brand_code")
                .where("ah_code", "=", singleQuote("240434"))
                .and("mch_code", "=", singleQuote("M10210301"))
                .and("brand_code", "=", singleQuote("AMP"))
                .build().nextPage("id", "0").sortBy("id", "ASC").limit("50");

        Assertions.assertEquals("SELECT id,liam,ah_code,mch_code,brand_code FROM products WHERE id > 0  AND ( ah_code = '240434' AND mch_code = 'M10210301' AND brand_code = 'AMP'  ) ORDER BY id ASC LIMIT 50", queryBuilder.get());

    }

    @Test
    @DisplayName("Given : basic OR query with ah_code, mch_code and brand_code with EQ operator, When: query is generated with combination, Then: SQL native query should return")
    public void testBasicQuery3() {

        QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam", "ah_code", "mch_code", "brand_code")
                .where("ah_code", "=", singleQuote("240434"))
                .or("mch_code", "=", singleQuote("M10210301"))
                .or("brand_code", "=", singleQuote("AMP"))
                .build();

        Assertions.assertEquals("SELECT id,liam,ah_code,mch_code,brand_code FROM products WHERE  ( ah_code = '240434' OR mch_code = 'M10210301' OR brand_code = 'AMP'  )", queryBuilder.get());

    }

    @Test
    @DisplayName("Given : basic OR query with ah_code, mch_code and brand_code with EQ operator, nextpage, order by and limit, When: query is generated with combination, Then: SQL native query should return")
    public void testBasicQuery4() {

        QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam", "ah_code", "mch_code", "brand_code")
                .where("ah_code", "=", singleQuote("240434"))
                .or("mch_code", "=", singleQuote("M10210301"))
                .or("brand_code", "=", singleQuote("AMP"))
                .build().nextPage("id", "0").sortBy("id", "ASC").limit("50");

        Assertions.assertEquals("SELECT id,liam,ah_code,mch_code,brand_code FROM products WHERE id > 0  AND ( ah_code = '240434' OR mch_code = 'M10210301' OR brand_code = 'AMP'  ) ORDER BY id ASC LIMIT 50", queryBuilder.get());

    }

    @Test
    @DisplayName("Given : basic AND query with ah_code, mch_code and brand_code with EQ operator containing multiple values, nextpage, order by and limit, When: query is generated with combination, Then: SQL native query should return")
    public void testBasicQuery5() {

        QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam", "ah_code", "mch_code", "brand_code")
                .where("ah_code", "=", singleQuote("240434"))
                .and("mch_code", "=", List.of(singleQuote("M10210301"), singleQuote("M10210302")))
                .and("brand_code", "=", List.of(singleQuote("AMP"), singleQuote("GTRD")))
                .build().nextPage("id", "0").sortBy("id", "ASC").limit("50");

        Assertions.assertEquals("SELECT id,liam,ah_code,mch_code,brand_code FROM products WHERE id > 0  AND ( ah_code = '240434' AND  ( mch_code = 'M10210301' OR mch_code = 'M10210302'  ) AND  ( brand_code = 'AMP' OR brand_code = 'GTRD'  )  ) ORDER BY id ASC LIMIT 50", queryBuilder.get());

    }

    @Test
    @DisplayName("Given : basic OR query with ah_code, mch_code and brand_code with EQ operator containing multiple values, nextpage, order by and limit, When: query is generated with combination, Then: SQL native query should return")
    public void testBasicQuery6() {

        QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam", "ah_code", "mch_code", "brand_code")
                .where("ah_code", "=", singleQuote("240434"))
                .or("mch_code", "=", List.of(singleQuote("M10210301"), singleQuote("M10210302")))
                .or("brand_code", "=", List.of(singleQuote("AMP"), singleQuote("GTRD")))
                .build().nextPage("id", "0").sortBy("id", "ASC").limit("50");

        Assertions.assertEquals("SELECT id,liam,ah_code,mch_code,brand_code FROM products WHERE id > 0  AND ( ah_code = '240434' OR  ( mch_code = 'M10210301' OR mch_code = 'M10210302'  ) OR  ( brand_code = 'AMP' OR brand_code = 'GTRD'  )  ) ORDER BY id ASC LIMIT 50", queryBuilder.get());

    }

    @Test
    @DisplayName("Given : basic AND subquery with ah_code, mch_code and brand_code with EQ operator, When: query is generated with combination, Then: SQL native query should return")
    public void testBasicSubQuery1() {

        QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam", "ah_code", "mch_code", "brand_code")
                .subquery()
                .where("ah_code", "=", singleQuote("240434"))
                .and("mch_code", "=", singleQuote("M10210301"))
                .and("brand_code", "=", singleQuote("AMP"))
                .build().build();

        Assertions.assertEquals("SELECT id,liam,ah_code,mch_code,brand_code FROM  ( SELECT id,liam,ah_code,mch_code,brand_code FROM products WHERE  ( ah_code = '240434' AND mch_code = 'M10210301' AND brand_code = 'AMP'  ) ) AS subquery", queryBuilder.get());

    }

    @Test
    @DisplayName("Given : basic AND subquery with ah_code, mch_code and brand_code with EQ operator with optimization fence OFFSET 0, When: query is generated with combination, Then: SQL native query should return")
    public void testBasicSubQuery2() {

        QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam", "ah_code", "mch_code", "brand_code")
                .subquery()
                .where("ah_code", "=", singleQuote("240434"))
                .and("mch_code", "=", singleQuote("M10210301"))
                .and("brand_code", "=", singleQuote("AMP"))
                .build().offset("0").build();

        Assertions.assertEquals("SELECT id,liam,ah_code,mch_code,brand_code FROM  ( SELECT id,liam,ah_code,mch_code,brand_code FROM products WHERE  ( ah_code = '240434' AND mch_code = 'M10210301' AND brand_code = 'AMP'  ) OFFSET 0 ) AS subquery", queryBuilder.get());

    }

    @Test
    @DisplayName("Given : basic OR subquery with ah_code, mch_code and brand_code with EQ operator, When: query is generated with combination, Then: SQL native query should return")
    public void testBasicSubQuery3() {

        QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam", "ah_code", "mch_code", "brand_code")
                .subquery()
                .where("ah_code", "=", singleQuote("240434"))
                .or("mch_code", "=", singleQuote("M10210301"))
                .or("brand_code", "=", singleQuote("AMP"))
                .build().build();

        Assertions.assertEquals("SELECT id,liam,ah_code,mch_code,brand_code FROM  ( SELECT id,liam,ah_code,mch_code,brand_code FROM products WHERE  ( ah_code = '240434' OR mch_code = 'M10210301' OR brand_code = 'AMP'  ) ) AS subquery", queryBuilder.get());

    }

    @Test
    @DisplayName("Given : basic OR subquery with ah_code, mch_code and brand_code with EQ operator with optimization fence, When: query is generated with combination, Then: SQL native query should return")
    public void testBasicSubQuery4() {

        QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam", "ah_code", "mch_code", "brand_code")
                .subquery()
                .where("ah_code", "=", singleQuote("240434"))
                .or("mch_code", "=", singleQuote("M10210301"))
                .or("brand_code", "=", singleQuote("AMP"))
                .build().offset("0").build();

        Assertions.assertEquals("SELECT id,liam,ah_code,mch_code,brand_code FROM  ( SELECT id,liam,ah_code,mch_code,brand_code FROM products WHERE  ( ah_code = '240434' OR mch_code = 'M10210301' OR brand_code = 'AMP'  ) OFFSET 0 ) AS subquery", queryBuilder.get());

    }

    @Test
    @DisplayName("Given : basic AND subquery with ah_code, mch_code and brand_code with EQ operator with optimization fence, nextpage within subquery When: query is generated with combination, Then: SQL native query should return")
    public void testBasicSubQuery5() {

        QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam", "ah_code", "mch_code", "brand_code")
                .subquery()
                .where("ah_code", "=", singleQuote("240434"))
                .and("mch_code", "=", singleQuote("M10210301"))
                .and("brand_code", "=", singleQuote("AMP"))
                .build().offset("0").nextPage("id", "0").build();

        Assertions.assertEquals("SELECT id,liam,ah_code,mch_code,brand_code FROM  ( SELECT id,liam,ah_code,mch_code,brand_code FROM products WHERE id > 0  AND ( ah_code = '240434' AND mch_code = 'M10210301' AND brand_code = 'AMP'  ) OFFSET 0 ) AS subquery", queryBuilder.get());

    }

    @Test
    @DisplayName("Given : basic AND subquery with ah_code, mch_code and brand_code with EQ operator with optimization fence, nextpage, limit, order by,  When: query is generated with combination, Then: SQL native query should return")
    public void testBasicSubQuery7() {

        QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam", "ah_code", "mch_code", "brand_code")
                .subquery()
                .where("ah_code", "=", singleQuote("240434"))
                .and("mch_code", "=", singleQuote("M10210301"))
                .and("brand_code", "=", singleQuote("AMP"))
                .build().offset("0").nextPage("id", "0").build().sortBy("id", "ASC").limit("50");

        Assertions.assertEquals("SELECT id,liam,ah_code,mch_code,brand_code FROM  ( SELECT id,liam,ah_code,mch_code,brand_code FROM products WHERE id > 0  AND ( ah_code = '240434' AND mch_code = 'M10210301' AND brand_code = 'AMP'  ) OFFSET 0 ) AS subquery ORDER BY id ASC LIMIT 50", queryBuilder.get());

    }

    @Test
    @DisplayName("Given : basic AND subquery with ah_code, mch_code and brand_code with EQ operator with multiple values, optimization fence, nextpage, limit, order by,  When: query is generated with combination, Then: SQL native query should return")
    public void testBasicSubQuery9() {

        QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam", "ah_code", "mch_code", "brand_code")
                .subquery()
                .where("ah_code", "=", singleQuote("240434"))
                .and("mch_code", "=", List.of(singleQuote("M10210301"), singleQuote("M10210302")))
                .and("brand_code", "=", List.of(singleQuote("AMP"), singleQuote("GTRD")))
                .build().offset("0").nextPage("id", "0").build().sortBy("id", "ASC").limit("50");

        Assertions.assertEquals("SELECT id,liam,ah_code,mch_code,brand_code FROM  ( SELECT id,liam,ah_code,mch_code,brand_code FROM products WHERE id > 0  AND ( ah_code = '240434' AND  ( mch_code = 'M10210301' OR mch_code = 'M10210302'  ) AND  ( brand_code = 'AMP' OR brand_code = 'GTRD'  )  ) OFFSET 0 ) AS subquery ORDER BY id ASC LIMIT 50", queryBuilder.get());

    }

    @Test
    @DisplayName("Given : basic AND subquery with ah_code, mch_code and brand_code with EQ operator with multiple values, optimization fence, nextpage within subquery When: query is generated with combination, Then: SQL native query should return")
    public void testBasicSubQuery6() {

        QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam", "ah_code", "mch_code", "brand_code")
                .subquery()
                .where("ah_code", "=", singleQuote("240434"))
                .and("mch_code", "=", List.of(singleQuote("M10210301"), singleQuote("M10210302")))
                .and("brand_code", "=", List.of(singleQuote("AMP"), singleQuote("GTRD")))
                .build().offset("0").nextPage("id", "0").build();

        Assertions.assertEquals("SELECT id,liam,ah_code,mch_code,brand_code FROM  ( SELECT id,liam,ah_code,mch_code,brand_code FROM products WHERE id > 0  AND ( ah_code = '240434' AND  ( mch_code = 'M10210301' OR mch_code = 'M10210302'  ) AND  ( brand_code = 'AMP' OR brand_code = 'GTRD'  )  ) OFFSET 0 ) AS subquery", queryBuilder.get());

    }

    @Test
    @DisplayName("Given : basic OR subquery with ah_code, mch_code and brand_code with EQ operator with multiple values, optimization fence, nextpage within subquery When: query is generated with combination, Then: SQL native query should return")
    public void testBasicSubQuery8() {

        QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam", "ah_code", "mch_code", "brand_code")
                .subquery()
                .where("ah_code", "=", singleQuote("240434"))
                .or("mch_code", "=", List.of(singleQuote("M10210301"), singleQuote("M10210302")))
                .or("brand_code", "=", List.of(singleQuote("AMP"), singleQuote("GTRD")))
                .build().offset("0").nextPage("id", "0").build();

        Assertions.assertEquals("SELECT id,liam,ah_code,mch_code,brand_code FROM  ( SELECT id,liam,ah_code,mch_code,brand_code FROM products WHERE id > 0  AND ( ah_code = '240434' OR  ( mch_code = 'M10210301' OR mch_code = 'M10210302'  ) OR  ( brand_code = 'AMP' OR brand_code = 'GTRD'  )  ) OFFSET 0 ) AS subquery", queryBuilder.get());

    }

    @Test
    @DisplayName("Given : subquery with brand_code with EQ operator, When: query is generated with combination, Then: SQL native query should return")
    public void testBasicSubQuery10() {

        QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam", "ah_code", "mch_code", "brand_code")
                .subquery()
                .where("brand_code", "=", singleQuote("AMP"))
                .build().build();

        Assertions.assertEquals("SELECT id,liam,ah_code,mch_code,brand_code FROM  ( SELECT id,liam,ah_code,mch_code,brand_code FROM products WHERE  ( brand_code = 'AMP'  ) ) AS subquery", queryBuilder.get());

    }

    @Test
    @DisplayName("Given : subquery with brand_code with EQ operator with multiple values, When: query is generated with combination, Then: SQL native query should return")
    public void testBasicSubQuery11() {

        QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam", "ah_code", "mch_code", "brand_code")
                .subquery()
                .where("brand_code", "=", List.of(singleQuote("AMP"), singleQuote("GTRD")))
                .build().build();

        Assertions.assertEquals("SELECT id,liam,ah_code,mch_code,brand_code FROM  ( SELECT id,liam,ah_code,mch_code,brand_code FROM products WHERE  (  ( brand_code = 'AMP' OR brand_code = 'GTRD'  )  ) ) AS subquery", queryBuilder.get());

    }


}
