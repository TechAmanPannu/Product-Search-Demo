package com.product.search.util.query;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.product.search.util.query.QueryUtils.singleQuote;

public class QueryBuilderTests {


    @Nested
    class BasicQueryTests {

        @Test
        @DisplayName("Given : brand_code with EQ operator, When: query is generated with combination, Then: SQL native query should return")
        public void testBasicQuery0() {

            QueryBuilder queryBuilder = new QueryBuilder("products", "brand_code")
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
                    .where()
                    .or("ah_code", "=", singleQuote("240434"))
                    .or("mch_code", "=", List.of(singleQuote("M10210301"), singleQuote("M10210302")))
                    .or("brand_code", "=", List.of(singleQuote("AMP"), singleQuote("GTRD")))
                    .build().nextPage("id", "0").sortBy("id", "ASC").limit("50");

            Assertions.assertEquals("SELECT id,liam,ah_code,mch_code,brand_code FROM products WHERE id > 0  AND ( ah_code = '240434' OR  ( mch_code = 'M10210301' OR mch_code = 'M10210302'  ) OR  ( brand_code = 'AMP' OR brand_code = 'GTRD'  )  ) ORDER BY id ASC LIMIT 50", queryBuilder.get());

        }

        @Test
        @DisplayName("Given : basic OR query with where(), ah_code, mch_code and brand_code with EQ operator containing multiple values, nextpage, order by and limit, When: query is generated with combination, Then: SQL native query should return")
        public void testBasicQuery7() {

            QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam", "ah_code", "mch_code", "brand_code")
                    .where()
                    .or("ah_code", "=", singleQuote("240434"))
                    .or("mch_code", "=", List.of(singleQuote("M10210301"), singleQuote("M10210302")))
                    .or("brand_code", "=", List.of(singleQuote("AMP"), singleQuote("GTRD")))
                    .build().nextPage("id", "0").sortBy("id", "ASC").limit("50");

            Assertions.assertEquals("SELECT id,liam,ah_code,mch_code,brand_code FROM products WHERE id > 0  AND ( ah_code = '240434' OR  ( mch_code = 'M10210301' OR mch_code = 'M10210302'  ) OR  ( brand_code = 'AMP' OR brand_code = 'GTRD'  )  ) ORDER BY id ASC LIMIT 50", queryBuilder.get());

        }

        @Test
        @DisplayName("Given : basic AND query with where(), ah_code, mch_code and brand_code with EQ operator containing multiple values, nextpage, order by and limit, When: query is generated with combination, Then: SQL native query should return")
        public void testBasicQuery8() {

            QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam", "ah_code", "mch_code", "brand_code")
                    .where()
                    .and("ah_code", "=", singleQuote("240434"))
                    .and("mch_code", "=", List.of(singleQuote("M10210301"), singleQuote("M10210302")))
                    .and("brand_code", "=", List.of(singleQuote("AMP"), singleQuote("GTRD")))
                    .build().nextPage("id", "0").sortBy("id", "ASC").limit("50");

            Assertions.assertEquals("SELECT id,liam,ah_code,mch_code,brand_code FROM products WHERE id > 0  AND ( ah_code = '240434' AND  ( mch_code = 'M10210301' OR mch_code = 'M10210302'  ) AND  ( brand_code = 'AMP' OR brand_code = 'GTRD'  )  ) ORDER BY id ASC LIMIT 50", queryBuilder.get());

        }
    }


    @Nested
    class BasicSubqueryTests {

        @Test
        @DisplayName("Given : basic AND subquery with ah_code, mch_code and brand_code with EQ operator, When: query is generated with combination, Then: SQL native query should return")
        public void testBasicSubQuery1() {

            QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam", "ah_code", "mch_code", "brand_code")
                    .subquery()
                    .where()
                    .and("ah_code", "=", singleQuote("240434"))
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
        @DisplayName("Given : basic AND subquery with ah_code, mch_code and brand_code with EQ operator with optimization fence, nextpage, order by within subquery When: query is generated with combination, Then: SQL native query should return")
        public void testBasicSubQuery5() {

            QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam", "ah_code", "mch_code", "brand_code")
                    .subquery()
                    .where("ah_code", "=", singleQuote("240434"))
                    .and("mch_code", "=", singleQuote("M10210301"))
                    .and("brand_code", "=", singleQuote("AMP"))
                    .build().sortBy("id", "ASC").offset("0").nextPage("id", "0").build();

            Assertions.assertEquals("SELECT id,liam,ah_code,mch_code,brand_code FROM  ( SELECT id,liam,ah_code,mch_code,brand_code FROM products WHERE id > 0  AND ( ah_code = '240434' AND mch_code = 'M10210301' AND brand_code = 'AMP'  ) ORDER BY id ASC OFFSET 0 ) AS subquery", queryBuilder.get());

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

        @Test
        @DisplayName("Given : basic AND subquery with dietary, ah_code, mch_code and brand_code with EQ operator with multiple values, optimization fence, nextpage within subquery When: query is generated with combination, Then: SQL native query should return")
        public void testBasicSubQuery12() {

            QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam", "ah_code", "mch_code", "brand_code")
                    .subquery()
                    .where("ah_code", "=", singleQuote("240434"))
                    .and("mch_code", "=", List.of(singleQuote("M10210301"), singleQuote("M10210302")))
                    .and("brand_code", "=", List.of(singleQuote("AMP"), singleQuote("GTRD")))
                    .andProductDietary("@@", List.of("peanut"))
                    .build().offset("0").nextPage("id", "0").build();

            Assertions.assertEquals("SELECT id,liam,ah_code,mch_code,brand_code FROM  ( SELECT id,liam,ah_code,mch_code,brand_code FROM products WHERE id > 0  AND ( ah_code = '240434' AND  ( mch_code = 'M10210301' OR mch_code = 'M10210302'  ) AND  ( brand_code = 'AMP' OR brand_code = 'GTRD'  ) AND  ( (jsonb_to_tsvector('english', enrichment -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('peanut') OR ((enrichment -> 'dietary_callouts' = '[]' OR enrichment -> 'dietary_callouts'= 'null') AND jsonb_to_tsvector('english', data -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('peanut') )) )  ) OFFSET 0 ) AS subquery", queryBuilder.get());

        }

        @Test
        @DisplayName("Given : basic OR subquery with ah_code, dietary, mch_code and brand_code with EQ operator with multiple values, optimization fence, nextpage within subquery When: query is generated with combination, Then: SQL native query should return")
        public void testBasicSubQuery13() {

            QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam", "ah_code", "mch_code", "brand_code")
                    .subquery()
                    .where("ah_code", "=", singleQuote("240434"))
                    .or("mch_code", "=", List.of(singleQuote("M10210301"), singleQuote("M10210302")))
                    .or("brand_code", "=", List.of(singleQuote("AMP"), singleQuote("GTRD")))
                    .orProductDietary("@@", List.of("peanut", "gluten"))
                    .build().offset("0").nextPage("id", "0").build();

            Assertions.assertEquals("SELECT id,liam,ah_code,mch_code,brand_code FROM  ( SELECT id,liam,ah_code,mch_code,brand_code FROM products WHERE id > 0  AND ( ah_code = '240434' OR  ( mch_code = 'M10210301' OR mch_code = 'M10210302'  ) OR  ( brand_code = 'AMP' OR brand_code = 'GTRD'  ) OR  ( (jsonb_to_tsvector('english', enrichment -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('peanut') OR ((enrichment -> 'dietary_callouts' = '[]' OR enrichment -> 'dietary_callouts'= 'null') AND jsonb_to_tsvector('english', data -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('peanut') ))OR (jsonb_to_tsvector('english', enrichment -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('gluten') OR ((enrichment -> 'dietary_callouts' = '[]' OR enrichment -> 'dietary_callouts'= 'null') AND jsonb_to_tsvector('english', data -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('gluten') )) )  ) OFFSET 0 ) AS subquery", queryBuilder.get());

        }


    }

    @Nested
    class BasicJoinSubqueryTests {

        @Test
        @DisplayName("Given : join query brand_code with EQ operator, When: query is generated with combination, Then: SQL native query should return")
        public void testBasicQuery0() {

            QueryBuilder queryBuilder = new QueryBuilder("products", "id, liam")
                    .joinSubquery("categories", "products.category_id = categories.id", "products.id", "products.liam")
                    .where()
                    .and("brand_code", "=", singleQuote("AMP"))
                    .build().build();

            Assertions.assertEquals("SELECT id, liam FROM  ( SELECT products.id,products.liam FROM products JOIN categories ON products.category_id = categories.id WHERE  ( brand_code = 'AMP'  ) ) AS subquery", queryBuilder.get());

        }

        @Test
        @DisplayName("Given : join query brand_code with EQ operator with multiple values, When: query is generated with combination, Then: SQL native query should return")
        public void testBasicQuery00() {

            QueryBuilder queryBuilder = new QueryBuilder("products", "id, liam")
                    .joinSubquery("categories", "products.category_id = categories.id", "products.id", "products.liam")
                    .where("brand_code", "=", List.of(singleQuote("AMP"), singleQuote("GTRD")))
                    .build().build();

            Assertions.assertEquals("SELECT id, liam FROM  ( SELECT products.id,products.liam FROM products JOIN categories ON products.category_id = categories.id WHERE  (  ( brand_code = 'AMP' OR brand_code = 'GTRD'  )  ) ) AS subquery", queryBuilder.get());

        }

        @Test
        @DisplayName("Given : join basic AND subquery with ah_code, mch_code and brand_code, category name along with optimization fence with offset 0, nextpage, order by limit and  with EQ operator, When: query is generated with combination, Then: SQL native query should return")
        public void testBasicSubQuery1() {

            QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam")
                    .joinSubquery("categories", "products.category_id = categories.id", "products.id", "products.liam")
                    .where()
                    .and("ah_code", "=", singleQuote("257497"))
                    .and("mch_code", "=", singleQuote("M10380306"))
                    .and("brand_code", "=", singleQuote("JAMI"))
                    .and("name ->> 'en'", "=", singleQuote("Vitamin C"))
                    .build().nextPage("products.id", "0").offset("0").build().sortBy("id", "ASC").limit("50");

            Assertions.assertEquals("SELECT id,liam FROM  ( SELECT products.id,products.liam FROM products JOIN categories ON products.category_id = categories.id WHERE products.id > 0  AND ( ah_code = '257497' AND mch_code = 'M10380306' AND brand_code = 'JAMI' AND name ->> 'en' = 'Vitamin C'  ) OFFSET 0 ) AS subquery ORDER BY subquery.id ASC LIMIT 50", queryBuilder.get());

        }

        @Test
        @DisplayName("Given : join basic AND subquery with ah_code, mch_code and brand_code, category name, dietary along with optimization fence with offset 0, nextpage, order by limit and  with EQ operator, When: query is generated with combination, Then: SQL native query should return")
        public void testBasicSubQuery2() {

            QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam")
                    .joinSubquery("categories", "products.category_id = categories.id", "products.id", "products.liam")
                    .where()
                    .and("products.ah_code", "=", singleQuote("240591"))
                    .and("products.mch_code", "=", singleQuote("M10210501"))
                    .and("products.brand_code", "=", singleQuote("CHRE"))
                    .and("categories.name ->> 'en'", "=", singleQuote("Kids Cookies"))
                    .andProductDietary("@@", List.of("peanut"))
                    .build().nextPage("products.id", "0").offset("0").build().sortBy("id", "ASC").limit("50");

            Assertions.assertEquals("SELECT id,liam FROM  ( SELECT products.id,products.liam FROM products JOIN categories ON products.category_id = categories.id WHERE products.id > 0  AND ( products.ah_code = '240591' AND products.mch_code = 'M10210501' AND products.brand_code = 'CHRE' AND categories.name ->> 'en' = 'Kids Cookies' AND  ( (jsonb_to_tsvector('english', products.enrichment -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('peanut') OR ((products.enrichment -> 'dietary_callouts' = '[]' OR products.enrichment -> 'dietary_callouts'= 'null') AND jsonb_to_tsvector('english', products.data -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('peanut') )) )  ) OFFSET 0 ) AS subquery ORDER BY subquery.id ASC LIMIT 50", queryBuilder.get());

        }

        @Test
        @DisplayName("Given : join query andDietary , When: query is generated with combination, Then: SQL native query should return")
        public void testBasicQuery3() {

            QueryBuilder queryBuilder = new QueryBuilder("products", "id, liam")
                    .joinSubquery("categories", "products.category_id = categories.id", "products.id", "products.liam")
                    .where()
                    .andProductDietary("@@", List.of("peanut"))
                    .build().build();

            Assertions.assertEquals("SELECT id, liam FROM  ( SELECT products.id,products.liam FROM products JOIN categories ON products.category_id = categories.id WHERE  (  ( (jsonb_to_tsvector('english', products.enrichment -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('peanut') OR ((products.enrichment -> 'dietary_callouts' = '[]' OR products.enrichment -> 'dietary_callouts'= 'null') AND jsonb_to_tsvector('english', products.data -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('peanut') )) )  ) ) AS subquery", queryBuilder.get());

        }

        @Test
        @DisplayName("Given : join query andDietary with multiple values, When: query is generated with combination, Then: SQL native query should return")
        public void testBasicQuery4() {

            QueryBuilder queryBuilder = new QueryBuilder("products", "id, liam")
                    .joinSubquery("categories", "products.category_id = categories.id", "products.id", "products.liam")
                    .where()
                    .andProductDietary("@@", List.of("peanut", "gluten"))
                    .build().build();

            Assertions.assertEquals("SELECT id, liam FROM  ( SELECT products.id,products.liam FROM products JOIN categories ON products.category_id = categories.id WHERE  (  ( (jsonb_to_tsvector('english', products.enrichment -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('peanut') OR ((products.enrichment -> 'dietary_callouts' = '[]' OR products.enrichment -> 'dietary_callouts'= 'null') AND jsonb_to_tsvector('english', products.data -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('peanut') ))OR (jsonb_to_tsvector('english', products.enrichment -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('gluten') OR ((products.enrichment -> 'dietary_callouts' = '[]' OR products.enrichment -> 'dietary_callouts'= 'null') AND jsonb_to_tsvector('english', products.data -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('gluten') )) )  ) ) AS subquery", queryBuilder.get());

        }


        @Test
        @DisplayName("Given : join basic AND subquery with ah_code, mch_code and brand_code, category name with multiple values when the operator is @@, dietary along with optimization fence with offset 0, nextpage, order by limit and  with EQ operator, When: query is generated with combination, Then: SQL native query should return")
        public void testBasicSubQuery5() {

            QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam")
                    .joinSubquery("categories", "products.category_id = categories.id", "products.id", "products.liam")
                    .where()
                    .and("products.ah_code", "=", singleQuote("240591"))
                    .and("products.mch_code", "=", singleQuote("M10210501"))
                    .and("products.brand_code", "=", singleQuote("CHRE"))
                    .and("jsonb_to_tsvector('english', categories.name -> CAST('en' AS TEXT), '[\"string\"]')", "@@", List.of(singleQuote("Kids Cookies"), singleQuote("Vitamin C")))
                    .andProductDietary("@@", List.of("peanut"))
                    .build().nextPage("products.id", "0").offset("0").build().sortBy("id", "ASC").limit("50");

            Assertions.assertEquals("SELECT id,liam FROM  ( SELECT products.id,products.liam FROM products JOIN categories ON products.category_id = categories.id WHERE products.id > 0  AND ( products.ah_code = '240591' AND products.mch_code = 'M10210501' AND products.brand_code = 'CHRE' AND  ( jsonb_to_tsvector('english', categories.name -> CAST('en' AS TEXT), '[\"string\"]') @@ to_tsquery('Kids&Cookies') OR jsonb_to_tsvector('english', categories.name -> CAST('en' AS TEXT), '[\"string\"]') @@ to_tsquery('Vitamin&C')  ) AND  ( (jsonb_to_tsvector('english', products.enrichment -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('peanut') OR ((products.enrichment -> 'dietary_callouts' = '[]' OR products.enrichment -> 'dietary_callouts'= 'null') AND jsonb_to_tsvector('english', products.data -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('peanut') )) )  ) OFFSET 0 ) AS subquery ORDER BY subquery.id ASC LIMIT 50", queryBuilder.get());

        }

        @Test
        @DisplayName("Given : join basic AND subquery with ah_code, mch_code and brand_code, category name with single value when the operator is @@, dietary along with optimization fence with offset 0, nextpage, order by limit and  with EQ operator, When: query is generated with combination, Then: SQL native query should return")
        public void testBasicSubQuery8() {

            QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam")
                    .joinSubquery("categories", "products.category_id = categories.id", "products.id", "products.liam")
                    .where()
                    .and("products.ah_code", "=", singleQuote("240591"))
                    .and("products.mch_code", "=", singleQuote("M10210501"))
                    .and("products.brand_code", "=", singleQuote("CHRE"))
                    .and("jsonb_to_tsvector('english', categories.name -> CAST('en' AS TEXT), '[\"string\"]')", "@@", singleQuote("Kids Cookies"))
                    .andProductDietary("@@", List.of("peanut"))
                    .build().nextPage("products.id", "0").offset("0").build().sortBy("id", "ASC").limit("50");

            Assertions.assertEquals("SELECT id,liam FROM  ( SELECT products.id,products.liam FROM products JOIN categories ON products.category_id = categories.id WHERE products.id > 0  AND ( products.ah_code = '240591' AND products.mch_code = 'M10210501' AND products.brand_code = 'CHRE' AND jsonb_to_tsvector('english', categories.name -> CAST('en' AS TEXT), '[\"string\"]') @@ to_tsquery('Kids&Cookies') AND  ( (jsonb_to_tsvector('english', products.enrichment -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('peanut') OR ((products.enrichment -> 'dietary_callouts' = '[]' OR products.enrichment -> 'dietary_callouts'= 'null') AND jsonb_to_tsvector('english', products.data -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('peanut') )) )  ) OFFSET 0 ) AS subquery ORDER BY subquery.id ASC LIMIT 50", queryBuilder.get());

        }

        @Test
        @DisplayName("Given : join basic AND subquery with ah_code, mch_code and brand_code, category name with multiple values when the operator is =, dietary along with optimization fence with offset 0, nextpage, order by limit and  with EQ operator, When: query is generated with combination, Then: SQL native query should return")
        public void testBasicSubQuery6() {

            QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam")
                    .joinSubquery("categories", "products.category_id = categories.id", "products.id", "products.liam")
                    .where()
                    .and("products.ah_code", "=", singleQuote("240591"))
                    .and("products.mch_code", "=", singleQuote("M10210501"))
                    .and("products.brand_code", "=", singleQuote("CHRE"))
                    .and("categories.name ->> 'en'", "=", List.of(singleQuote("Kids Cookies"), singleQuote("Vitamin C")))
                    .andProductDietary("@@", List.of("peanut"))
                    .build().nextPage("products.id", "0").offset("0").build().sortBy("id", "ASC").limit("50");

            Assertions.assertEquals("SELECT id,liam FROM  ( SELECT products.id,products.liam FROM products JOIN categories ON products.category_id = categories.id WHERE products.id > 0  AND ( products.ah_code = '240591' AND products.mch_code = 'M10210501' AND products.brand_code = 'CHRE' AND  ( categories.name ->> 'en' = 'Kids Cookies' OR categories.name ->> 'en' = 'Vitamin C'  ) AND  ( (jsonb_to_tsvector('english', products.enrichment -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('peanut') OR ((products.enrichment -> 'dietary_callouts' = '[]' OR products.enrichment -> 'dietary_callouts'= 'null') AND jsonb_to_tsvector('english', products.data -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('peanut') )) )  ) OFFSET 0 ) AS subquery ORDER BY subquery.id ASC LIMIT 50", queryBuilder.get());

        }

        @Test
        @DisplayName("Given : join basic AND subquery with ah_code, mch_code and brand_code, category name with single value when the operator is =, dietary along with optimization fence with offset 0, nextpage, order by limit and  with EQ operator, When: query is generated with combination, Then: SQL native query should return")
        public void testBasicSubQuery7() {

            QueryBuilder queryBuilder = new QueryBuilder("products", "id", "liam")
                    .joinSubquery("categories", "products.category_id = categories.id", "products.id", "products.liam")
                    .where()
                    .and("products.ah_code", "=", singleQuote("240591"))
                    .and("products.mch_code", "=", singleQuote("M10210501"))
                    .and("products.brand_code", "=", singleQuote("CHRE"))
                    .and("categories.name ->> 'en'", "=",  singleQuote("Vitamin C"))
                    .andProductDietary("@@", List.of("peanut"))
                    .build().nextPage("products.id", "0").offset("0").build().sortBy("id", "ASC").limit("50");

            Assertions.assertEquals("SELECT id,liam FROM  ( SELECT products.id,products.liam FROM products JOIN categories ON products.category_id = categories.id WHERE products.id > 0  AND ( products.ah_code = '240591' AND products.mch_code = 'M10210501' AND products.brand_code = 'CHRE' AND  ( name ->> 'en' = 'Kids Cookies' OR name ->> 'en' = 'Vitamin C'  ) AND  ( (jsonb_to_tsvector('english', products.enrichment -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('peanut') OR ((products.enrichment -> 'dietary_callouts' = '[]' OR products.enrichment -> 'dietary_callouts'= 'null') AND jsonb_to_tsvector('english', products.data -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('peanut') )) )  ) OFFSET 0 ) AS subquery ORDER BY subquery.id ASC LIMIT 50", queryBuilder.get());

        }
    }


    @Test
    void test() {
        System.out.println(QueryUtils.createOrDietaryCondition("@@", List.of("peanut", "butter"), false));
    }


}
