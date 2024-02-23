package com.product.search.util.query;

import java.util.List;

import static com.product.search.util.query.QueryConstants.NEXT_PAGE_PATTERN;
import static com.product.search.util.query.QueryConstants.SUBQUERY_LITERAL;
import static com.product.search.util.query.QueryUtils.createQuery;
import static com.product.search.util.query.QueryUtils.join;

public class QueryBuilder {

    private String tableName;
    private String[] columns;
    private String query;
    private boolean isJoinSubqueryQuery;
    private String nextPage;



    public QueryBuilder(String tableName) {
        this(tableName, "*");
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

    public WhereClause where(String property, String operator, List<String> values) {
        WhereClause whereClause = new WhereClause(this, property, operator, values);
        this.query = query + whereClause.build().get();
        return whereClause;
    }

    public SubqueryBuilder subquery() {
        return new SubqueryBuilder(this, tableName, columns);
    }


    public SubqueryBuilder joinSubquery(String anotherTableName, String joinOn, String... columns) {
        this.isJoinSubqueryQuery = true;
        return new SubqueryBuilder(this, anotherTableName, joinOn, columns, true);
    }

    public SubqueryBuilder joinSubquery(String anotherTableName, String joinOn) {
        return joinSubquery(anotherTableName, joinOn, "*");
    }

    public QueryBuilder sortBy(String property, String order) {
        this.query = this.isJoinSubqueryQuery ? String.format("%s ORDER BY %s.%s %s", this.query, SUBQUERY_LITERAL, property, order) : String.format("%s ORDER BY %s %s", this.query, property, order);
        return this;
    }

    public QueryBuilder limit(String value) {
        this.query = String.format("%s LIMIT %s", this.query, value);
        return this;
    }

    public QueryBuilder nextPage(String property, String value) {
        this.nextPage = String.format("%s AND", createQuery(property, ">", value));
        return this;
    }


    public String get() {
        if (nextPage != null && !nextPage.isEmpty()) {
            this.query = this.query.replaceFirst(NEXT_PAGE_PATTERN, this.nextPage);
        } else {
            this.query = this.query.replaceFirst(NEXT_PAGE_PATTERN, "");
        }
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
