package com.product.search.util.query;

import static com.product.search.util.query.QueryUtils.join;

public class QueryBuilder {

    private String tableName;
    private SubqueryBuilder subqueryBuilder;

    private String[] columns;
    private String query;


    public QueryBuilder(String query) {
        this.query = query;
    }

    public QueryBuilder(String tableName, String... columns) {
        this.query = String.format("SELECT %s FROM %s ", join(",", columns), tableName);
        this.tableName = tableName;
        this.columns = columns;
    }


    public WhereClause where(String property, String operator, String value) {
        WhereClause whereClause = new WhereClause(this, property, operator, value);
        this.query = query + whereClause.build().get();
        return whereClause;
    }

    public SubqueryBuilder subquery(String tableName, String... columns) {
        return new SubqueryBuilder(this,tableName, columns);
    }


    public QueryBuilder sortBy(String property, String order) {
        this.query = String.format("%s ORDER BY %s %s", this.query, property, order);
        return this;
    }

    public String get() {
        return this.query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String tableName() {
        return this.tableName;
    }


}
