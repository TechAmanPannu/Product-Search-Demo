package com.product.search.util.query;

import static com.product.search.util.query.QueryUtils.join;

public class QueryBuilder {

    private String tableName;
    private String[] columns;
    private String query;
    private boolean isJoinSubqueryQuery;
    public static final String SUBQUERY_LITERAL = "subquery";


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
        return new SubqueryBuilder(this, tableName, columns);
    }


    public SubqueryBuilder joinSubquery(String anotherTableName, String joinOn, String... columns) {
        this.isJoinSubqueryQuery = true;
        return new SubqueryBuilder(this, anotherTableName, joinOn, columns, true);
    }

    public QueryBuilder sortBy(String property, String order) {
        this.query = this.isJoinSubqueryQuery ? String.format("%s ORDER BY %s.%s %s", this.query, SUBQUERY_LITERAL, property, order) : String.format("%s ORDER BY %s %s", this.query, property, order);
        return this;
    }

    public QueryBuilder limit(String value) {
        this.query = String.format("%s LIMIT %s", this.query, value);
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

    public String[] columns() {
        return this.columns;
    }


}
