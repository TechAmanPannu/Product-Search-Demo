package com.product.search.util.query;

import java.util.List;

import static com.product.search.util.query.QueryConstants.NEXT_PAGE_PATTERN;
import static com.product.search.util.query.QueryUtils.*;

public class SubqueryWhereClause  {

    private String query;
    private SubqueryBuilder subqueryBuilder;
    public SubqueryWhereClause( SubqueryBuilder subqueryBuilder,  String property, String operator, String value) {
        this.subqueryBuilder = subqueryBuilder;
        this.query =  String.format("%sWHERE %s ( %s", this.subqueryBuilder.get(), NEXT_PAGE_PATTERN, createQuery(property, operator, value));
    }

    public SubqueryWhereClause and(String property, String operator, String value) {
        this.query = String.format("%sAND %s",this.query, createQuery(property, operator, value));
        return this;
    }

    public SubqueryWhereClause and(String property, String operator, List<String> values) {
        this.query = String.format("%sAND %s", this.query, createORQuery(property, operator, values));
        return this;
    }



    public SubqueryWhereClause or(String property, String operator, String value) {
        this.query = String.format("%sOR %s",this.query, createQuery(property, operator, value));
        return this;
    }

    public SubqueryWhereClause or(String property, String operator, List<String> values) {
        this.query = String.format("%sOR %s", this.query, createORQuery(property, operator, values));
        return this;
    }

    public SubqueryWhereClause andDietary(String operator, List<String> values) {
        this.query = String.format("%sAND (jsonb_to_tsvector('english', enrichment -> 'dietary_callouts', '[\"string\"]') %s to_tsquery('%s') OR " +
                "((enrichment -> 'dietary_callouts' = '[]' OR enrichment -> 'dietary_callouts'= 'null') " +
                "AND jsonb_to_tsvector('english', data -> 'dietary_callouts', '[\"string\"]') %s to_tsquery('%s') ))",this.query, operator, join("|", values), operator, join("|", values));
        return this;
    }


    public SubqueryWhereClause orDietary(String operator, List<String> values) {
        this.query = String.format("%sOR (jsonb_to_tsvector('english', enrichment -> 'dietary_callouts', '[\"string\"]') %s to_tsquery('%s') OR " +
                "((enrichment -> 'dietary_callouts' = '[]' OR enrichment -> 'dietary_callouts'= 'null') " +
                "AND jsonb_to_tsvector('english', data -> 'dietary_callouts', '[\"string\"]') %s to_tsquery('%s') ))",this.query, operator, join("|", values), operator, join("|", values));
        return this;
    }


    public SubqueryBuilder build() {
        this.subqueryBuilder.setQuery(this.query + " )");
       return subqueryBuilder;
    }

    public String get() {
        return this.query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    private String getDietaryQuery() {
        return "SELECT id,liam FROM  ( SELECT id,liam FROM products WHERE id > 0  AND ( mch_code = '977677788' " +
                "AND jsonb_to_tsvector('english', enrichment -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('peanut') OR ((enrichment -> 'dietary_callouts' = '[]' OR enrichment -> 'dietary_callouts'= 'null') " +
                "AND jsonb_to_tsvector('english', data -> 'dietary_callouts', '[\"string\"]') @@ to_tsquery('peanut') )AND brand_code = '78766876'  ) OFFSET 0 ) AS subquery ORDER BY id ASC LIMIT 50";
    }
}
