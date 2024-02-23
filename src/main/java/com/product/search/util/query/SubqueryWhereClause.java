package com.product.search.util.query;

import java.util.List;

import static com.product.search.util.query.QueryConstants.*;
import static com.product.search.util.query.QueryUtils.*;

public class SubqueryWhereClause {

    private String query;
    private SubqueryBuilder subqueryBuilder;

    public SubqueryWhereClause(SubqueryBuilder subqueryBuilder, String property, String operator, String value) {
        this.subqueryBuilder = subqueryBuilder;
        this.query = String.format("%sWHERE %s ( %s", this.subqueryBuilder.get(), NEXT_PAGE_PATTERN, createQuery(property, operator, value));
    }

    public SubqueryWhereClause(SubqueryBuilder subqueryBuilder, String property, String operator, List<String> values) {
        this.subqueryBuilder = subqueryBuilder;
        this.query = String.format("%sWHERE %s ( %s", this.subqueryBuilder.get(), NEXT_PAGE_PATTERN, createORQuery(property, operator, values));
    }

    public SubqueryWhereClause and(String property, String operator, String value) {
        this.query = String.format("%sAND %s", this.query, createQuery(property, operator, value));
        return this;
    }

    public SubqueryWhereClause and(String property, String operator, List<String> values) {
        this.query = String.format("%sAND %s", this.query, createORQuery(property, operator, values));
        return this;
    }


    public SubqueryWhereClause or(String property, String operator, String value) {
        this.query = String.format("%sOR %s", this.query, createQuery(property, operator, value));
        return this;
    }

    public SubqueryWhereClause or(String property, String operator, List<String> values) {
        this.query = String.format("%sOR %s", this.query, createORQuery(property, operator, values));
        return this;
    }

    public SubqueryWhereClause andProductDietary(String operator, List<String> values) {

        this.query = this.subqueryBuilder.isJoinQuery() ? String.format("%sAND " + PRODUCTS__DIETARY_JOIN_QUERY, this.query, operator, join("|", values), operator, join("|", values))
                : String.format("%sAND "+PRODUCTS_DIETARY_QUERY, this.query, operator, join("|", values), operator, join("|", values));
        return this;
    }


    public SubqueryWhereClause orProductDietary(String operator, List<String> values) {
        this.query = this.subqueryBuilder.isJoinQuery() ? String.format("%sOR " + PRODUCTS__DIETARY_JOIN_QUERY, this.query, operator, join("|", values), operator, join("|", values))
                : String.format("%sOR "+PRODUCTS_DIETARY_QUERY, this.query, operator, join("|", values), operator, join("|", values));
        return this;
    }


    public SubqueryBuilder build() {
        this.subqueryBuilder.setQuery(this.query + " )");
        return subqueryBuilder;
    }

    public String get() {
        return this.query;
    }


}
