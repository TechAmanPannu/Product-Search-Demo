package com.product.search.util.query;

import java.util.List;

import static com.product.search.util.query.QueryConstants.NEXT_PAGE_PATTERN;
import static com.product.search.util.query.QueryUtils.*;

public class WhereClause {

    protected QueryBuilder queryBuilder;
    private String query;

    private boolean whereWithoutCondition;


    public WhereClause(QueryBuilder queryBuilder, String property, String operator, String value) {
        this.queryBuilder = queryBuilder;
        this.query = String.format("%sWHERE %s ( %s", this.queryBuilder.get(), NEXT_PAGE_PATTERN, createCondition(property, operator, value));
    }

    public WhereClause(QueryBuilder queryBuilder, boolean whereWithoutCondition) {
        this.queryBuilder = queryBuilder;
        this.whereWithoutCondition = whereWithoutCondition;
        this.query = String.format("%sWHERE %s ( ", this.queryBuilder.get(), NEXT_PAGE_PATTERN);
    }

    public WhereClause(QueryBuilder queryBuilder, String property, String operator, List<String> values) {
        this.queryBuilder = queryBuilder;
        this.query = String.format("%sWHERE %s ( %s", this.queryBuilder.get(), NEXT_PAGE_PATTERN, createOrCondition(property, operator, values));
    }

    public WhereClause and(String property, String operator, String value) {
        if(operator == null || value == null || value.isEmpty()) {
            return this;
        }
        query = String.format("%sAND %s", this.query, createCondition(property, operator, value));
        return this;
    }

    public WhereClause and(String property, String operator, List<String> values) {
        if(operator == null || values == null || values.isEmpty()) {
            return this;
        }
        query = String.format("%sAND %s", this.query, createOrCondition(property, operator, values));
        return this;
    }


    public WhereClause or(String property, String operator, String value) {
        if(operator == null || value == null || value.isEmpty()) {
            return this;
        }
        query = String.format("%sOR %s", this.query, createCondition(property, operator, value));
        return this;
    }

    public WhereClause or(String property, String operator, List<String> values) {
        if(operator == null || values == null || values.isEmpty()) {
            return this;
        }
        query = String.format("%sOR %s", this.query, createOrCondition(property, operator, values));
        return this;
    }


    public QueryBuilder build() {

        if(whereWithoutCondition) {
            this.query = this.query.replaceFirst("\\( OR", "(");
            this.query = this.query.replaceFirst("\\( AND", "(");
        }
        this.queryBuilder.setQuery(this.query + " )");
        return this.queryBuilder;
    }


}
