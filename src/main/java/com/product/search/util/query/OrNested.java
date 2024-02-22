package com.product.search.util.query;

import static com.product.search.util.query.QueryUtils.createQuery;

public class OrNested {

    private String query;
    private SubqueryWhereClause subqueryWhereClause;

    public OrNested(SubqueryWhereClause subqueryWhereClause, String property, String operator, String value) {
        this.subqueryWhereClause = subqueryWhereClause;
        this.query = query + String.format("( %s", createQuery(property, operator, value));
    }

    public OrNested or(String property, String operator, String value) {
        this.query = String.format("%sOR %s",this.query, createQuery(property, operator, value));
        return this;
    }

    public SubqueryWhereClause build() {
        this.query = this.query + " ) ";
        this.subqueryWhereClause.setQuery(this.query);
        return subqueryWhereClause;
    }

    public String get() {
        return this.query;
    }

}
