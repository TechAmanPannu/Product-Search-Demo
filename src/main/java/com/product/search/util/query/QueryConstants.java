package com.product.search.util.query;

import java.util.Set;

public class QueryConstants {

    public static final String NEXT_PAGE_PATTERN = "_nextpage_";

    public static final String SUBQUERY_LITERAL = "subquery";


    public static final String PRODUCTS_DIETARY_QUERY = "(jsonb_to_tsvector('english', enrichment -> 'dietary_callouts', '[\"string\"]') %s to_tsquery('%s') OR ((enrichment -> 'dietary_callouts' = '[]' " +
            "OR enrichment -> 'dietary_callouts'= 'null') AND jsonb_to_tsvector('english', data -> 'dietary_callouts', '[\"string\"]') %s to_tsquery('%s') ))";

    public static final String PRODUCTS__DIETARY_JOIN_QUERY = "(jsonb_to_tsvector('english', products.enrichment -> 'dietary_callouts', '[\"string\"]') %s to_tsquery('%s') OR ((products.enrichment -> 'dietary_callouts' = '[]' " +
            "OR products.enrichment -> 'dietary_callouts'= 'null') AND jsonb_to_tsvector('english', products.data -> 'dietary_callouts', '[\"string\"]') %s to_tsquery('%s') ))";


    public static final Set<String> TS_TOQUERY_OPERATORS = Set.of("@@");
}
