package com.product.search.util.query;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static com.product.search.util.query.QueryConstants.*;
import static com.product.search.util.query.QueryUtils.*;

public class SubqueryWhereClause {

    private String query;
    private SubqueryBuilder subqueryBuilder;

    private boolean whereWithoutCondition;

    public SubqueryWhereClause(SubqueryBuilder subqueryBuilder, boolean whereWithoutCondition) {
        this.subqueryBuilder = subqueryBuilder;
        this.whereWithoutCondition = whereWithoutCondition;
        this.query = String.format("%sWHERE %s ( ", this.subqueryBuilder.get(), NEXT_PAGE_PATTERN);
    }

    public SubqueryWhereClause(SubqueryBuilder subqueryBuilder, String property, String operator, String value) {
        this.subqueryBuilder = subqueryBuilder;
        this.query = String.format("%sWHERE %s ( %s", this.subqueryBuilder.get(), NEXT_PAGE_PATTERN, createCondition(property, operator, value));
    }

    public SubqueryWhereClause(SubqueryBuilder subqueryBuilder, String property, String operator, List<String> values) {
        this.subqueryBuilder = subqueryBuilder;
        this.query = String.format("%sWHERE %s ( %s", this.subqueryBuilder.get(), NEXT_PAGE_PATTERN, createOrCondition(property, operator, values));
    }

    public SubqueryWhereClause and(String property, String operator, String value) {
        if(operator == null || value == null || value.isEmpty()) {
            return this;
        }
        this.query = String.format("%sAND %s", this.query, createCondition(property, operator, value));
        return this;
    }

    public SubqueryWhereClause and(String property, String operator, List<String> values) {
        if(operator == null || values == null || values.isEmpty()) {
            return this;
        }
        this.query = String.format("%sAND %s", this.query, createOrCondition(property, operator, values));
        return this;
    }


    public SubqueryWhereClause or(String property, String operator, String value) {
        if(operator == null || value == null || value.isEmpty()) {
            return this;
        }
        this.query = String.format("%sOR %s", this.query, createCondition(property, operator, value));
        return this;
    }

    public SubqueryWhereClause or(String property, String operator, List<String> values) {
        if(operator == null || values == null || values.isEmpty()) {
            return this;
        }
        this.query = String.format("%sOR %s", this.query, createOrCondition(property, operator, values));
        return this;
    }

    public SubqueryWhereClause andProductDietary(String operator, List<String> values) {
        if(operator == null || values == null || values.isEmpty()) {
            return this;
        }
        this.query = String.format("%sAND %s", this.query, createOrDietaryCondition(operator, values, this.subqueryBuilder.isJoinQuery()));
        return this;
    }

    public SubqueryWhereClause orProductDietary(String operator, List<String> values) {
        if(operator == null || values == null || values.isEmpty()) {
            return this;
        }
        this.query = String.format("%sOR %s", this.query, createOrDietaryCondition(operator, values, this.subqueryBuilder.isJoinQuery()));
        return this;
    }


    public SubqueryBuilder build() {
        if (whereWithoutCondition) {
            this.query = this.query.replaceFirst("\\( AND", "(");
            this.query = this.query.replaceFirst("\\( OR", "(");
        }

        this.query = this.query + " )";
        this.subqueryBuilder.setQuery(this.query);
        return subqueryBuilder;
    }

    public String get() {
        return this.query;
    }


}
