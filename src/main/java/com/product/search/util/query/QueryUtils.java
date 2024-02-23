package com.product.search.util.query;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

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
        return String.format("%s %s %s ", property, operator, value);
    }

    static String createOrCondition(String property, String operator, List<String> values) {
        List<String> operations = new ArrayList<>();
        for (String value : values) {
            operations.add(String.format("%s %s %s ", property, operator, value));
        }
        return String.format(" ( %s ) ", join("OR ", operations)) ;
    }

    static String singleQuote(String str) {
        return String.format("'%s'", str);
    }
}
