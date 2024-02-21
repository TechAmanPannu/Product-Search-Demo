package com.product.search.util.query;

import static com.product.search.util.query.QueryUtils.createQuery;

public class SubqueryWhereClause  {

    private String query;
    private SubqueryBuilder subqueryBuilder;
    public SubqueryWhereClause( SubqueryBuilder subqueryBuilder,  String property, String operator, String value) {
        this.subqueryBuilder = subqueryBuilder;
        this.query =  String.format("%sWHERE %s", this.subqueryBuilder.get(), createQuery(property, operator, value));
    }

    public SubqueryWhereClause and(String property, String operator, String value) {
        this.query = String.format("%sAND %s",this.query, createQuery(property, operator, value));
        return this;
    }

    public SubqueryWhereClause or(String property, String operator, String value) {
        this.query = String.format("%sOR %s",this.query, createQuery(property, operator, value));
        return this;
    }

    public SubqueryBuilder build() {
        this.subqueryBuilder.setQuery(this.query);
       return subqueryBuilder;
    }

    public String get() {
        return this.query;
    }
}
