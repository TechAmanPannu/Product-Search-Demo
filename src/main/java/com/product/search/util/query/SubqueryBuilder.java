package com.product.search.util.query;

import static com.product.search.util.query.QueryUtils.join;

public class SubqueryBuilder {

    private QueryBuilder queryBuilder;
    private String query;


    public SubqueryBuilder( QueryBuilder builder, String tableName, String[] columns) {
        this.queryBuilder = builder;
        this.query =  String.format("( SELECT %s FROM %s ", join(",", columns), tableName);

    }

    public SubqueryWhereClause where(String property, String operator, String value) {
        SubqueryWhereClause whereClause = new SubqueryWhereClause(this.query, this, property, operator, value);
        this.query =  this.query + whereClause.get();
        return whereClause;
    }



    public SubqueryBuilder sortBy(String property, String order) {
        this.query = String.format("%s ORDER BY %s %s", this.query, property, order);
        return this;
    }


    public SubqueryBuilder offset(String value) {
        this.query = String.format("%s OFFSET %s", this.query, value);
        return this;
    }

    public void setQuery(String query) {
        this.query = query;
    }
    public String get() {
        return this.query;
    }


    public QueryBuilder build() {
        this.queryBuilder.setQuery((this.queryBuilder.get() + this.query + " ) AS subquery").replaceFirst(this.queryBuilder.tableName(), ""));
        return this.queryBuilder;
    }

}
