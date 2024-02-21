package com.product.search.util.query;

import java.util.StringJoiner;

public interface QueryUtils {

     static String join(String delimeter, String[] values) {
        StringJoiner stringJoiner = new StringJoiner(delimeter);
        for (String value : values) {
            stringJoiner.add(value);
        }
        return stringJoiner.toString();
    }

    static String createQuery(String property, String operator, String value) {
        return String.format("%s %s '%s' ", property, operator, value);
    }
}
