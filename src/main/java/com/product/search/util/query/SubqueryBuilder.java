package com.product.search.util.query;



import java.util.List;

import static com.product.search.util.query.QueryConstants.NEXT_PAGE_PATTERN;
import static com.product.search.util.query.QueryConstants.SUBQUERY_LITERAL;
import static com.product.search.util.query.QueryUtils.createQuery;
import static com.product.search.util.query.QueryUtils.join;

public class SubqueryBuilder {

    private QueryBuilder queryBuilder;
    private String query;

    private String nextPage;

    private boolean isJoinQuery;

    public SubqueryBuilder(QueryBuilder builder, String tableName, String[] columns) {
        this(builder, tableName, null, columns, false);
    }

    public SubqueryBuilder(QueryBuilder builder, String tableName, String joinOn, String[] columns, boolean isJoinQuery) {
        this.queryBuilder = builder;
        this.isJoinQuery = isJoinQuery;
        this.query = isJoinQuery ?
                String.format("( SELECT %s FROM %s JOIN %s ON %s ", join(",", columns), builder.tableName(), tableName, joinOn)
                : String.format("( SELECT %s FROM %s ", join(",", builder.columns()), builder.tableName());
    }

    public SubqueryWhereClause where(String property, String operator, String value) {
        SubqueryWhereClause whereClause = new SubqueryWhereClause(this, property, operator, value);
        this.query = this.query + whereClause.get();
        return whereClause;
    }

    public SubqueryWhereClause where(String property, String operator, List<String> values) {
        SubqueryWhereClause whereClause = new SubqueryWhereClause(this, property, operator, values);
        this.query = this.query + whereClause.get();
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

    public SubqueryBuilder nextPage(String property, String value) {
        this.nextPage = String.format("%s AND", createQuery(property, ">", value));
        return this;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String get() {
        return this.query;
    }


    public QueryBuilder build() {
        String tempQuery = String.format("%s%s ) AS %s", this.queryBuilder.get(), this.query, SUBQUERY_LITERAL).replaceFirst(this.queryBuilder.tableName(), "");
        if (nextPage != null && !nextPage.isEmpty()) {
            tempQuery = tempQuery.replaceFirst(NEXT_PAGE_PATTERN, this.nextPage);
        } else {
            tempQuery = tempQuery.replaceFirst(NEXT_PAGE_PATTERN, "");
        }
        this.queryBuilder.setQuery(tempQuery);
        return this.queryBuilder;
    }

    public boolean isJoinQuery() {
        return this.isJoinQuery;
    }


}
