package com.product.search.util.query;

import static com.product.search.util.query.QueryUtils.createQuery;

public class WhereClause {

    protected QueryBuilder queryBuilder;
    private String query;
    public WhereClause(QueryBuilder queryBuilder, String property, String operator, String value) {
        this.queryBuilder = queryBuilder;
        this.query = queryBuilder.get() + "WHERE " + createQuery(property, operator, value);
    }


    public WhereClause and(String property, String operator, String value) {
        query = query + "AND " + createQuery(property, operator, value);
        return this;
    }

    public WhereClause or(String property, String operator, String value) {
        query = query + "OR " + createQuery(property, operator, value);
        return this;
    }



    public QueryBuilder build() {
        this.queryBuilder.setQuery(this.query);
        return this.queryBuilder;
    }


}
