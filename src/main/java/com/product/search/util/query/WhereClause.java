package com.product.search.util.query;

import java.util.ArrayList;
import java.util.List;

import static com.product.search.util.query.QueryConstants.NEXT_PAGE_PATTERN;
import static com.product.search.util.query.QueryUtils.*;

public class WhereClause {

    protected QueryBuilder queryBuilder;
    private String query;


    public WhereClause(QueryBuilder queryBuilder, String property, String operator, String value) {
        this.queryBuilder = queryBuilder;
        this.query = String.format("%sWHERE %s ( %s", this.queryBuilder.get(), NEXT_PAGE_PATTERN, createQuery(property, operator, value));
    }

    public WhereClause and(String property, String operator, String value) {
        query = String.format("%sAND %s", this.query, createQuery(property, operator, value));
        return this;
    }

    public WhereClause and(String property, String operator, List<String> values) {
        query = String.format("%sAND %s", this.query, createORQuery(property, operator, values));
        return this;
    }


    public WhereClause or(String property, String operator, String value) {
        query = String.format("%sOR %s", this.query, createQuery(property, operator, value));
        return this;
    }


    public QueryBuilder build() {
        this.queryBuilder.setQuery(this.query + " )");
        return this.queryBuilder;
    }


}
