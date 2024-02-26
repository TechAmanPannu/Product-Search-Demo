package com.product.search.util.query;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static com.product.search.util.query.QueryConstants.*;

public interface QueryUtils {

    static String join(String delimeter, String[] values) {
        if (values.length == 0) return "";
        StringJoiner stringJoiner = new StringJoiner(delimeter);
        for (String value : values) {
            stringJoiner.add(value);
        }
        return stringJoiner.toString();
    }

    static String join(String delimeter, List<String> values) {
        if (values.isEmpty()) return "";
        StringJoiner stringJoiner = new StringJoiner(delimeter);
        for (String value : values) {
            stringJoiner.add(value);
        }
        return stringJoiner.toString();
    }




    static String createCondition(String property, String operator, String value) {
        if(TS_TOQUERY_OPERATORS.contains(operator)) {
            value = getTSQueryValue(value);
        }
        return String.format("%s %s %s ", property, operator, value);
    }

    static String getTSQueryValue(String value) {
        return String.format("to_tsquery(%s)", value);
    }

    static String createOrCondition(String property, String operator, List<String> values) {
        List<String> conditions = new ArrayList<>();

        boolean isTSQuery = false;
        if(TS_TOQUERY_OPERATORS.contains(operator)) {
            isTSQuery = true;
        }
        for (String value : values) {
            if(isTSQuery) {
                value = getTSQueryValue(join("&", value.split(" ")));
            }
            conditions.add(String.format("%s %s %s ", property, operator, value));
        }
        return String.format(" ( %s ) ", join("OR ", conditions)) ;
    }

    static String createOrDietaryCondition(String operator, List<String> values, boolean isJoinQuery) {
        List<String> operations = new ArrayList<>();
        for (String value : values) {
            value = join("&", value.split(" "));
            operations.add(String.format(isJoinQuery ? PRODUCTS__DIETARY_JOIN_QUERY :
                    PRODUCTS_DIETARY_QUERY, operator, value, operator, value));
        }
        return String.format(" ( %s ) ", join("OR ", operations)) ;
    }

    static String singleQuote(String str) {
        return String.format("'%s'", str);
    }
}
