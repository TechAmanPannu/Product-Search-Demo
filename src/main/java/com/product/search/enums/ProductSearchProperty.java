//package com.product.search.enums;
//
//import java.util.List;
//
//public enum ProductSearchProperty {
//
//    BRAND("products", List.of("brand_code"), false, false),
//    PRODUCT_COLOR("products", List.of("variant_data.color.en"), true, false),
//    PRODUCT_CATEGORY("categories", List.of("name.en"), false, true, "products"),
//    SKIN_CONCERN("products", List.of("enrichment.specifications.code.concern"), true, false),
//    DIETARY("products", List.of("enrichment.dietary_callouts", "data.dietary_callouts"), true, ),
//
//    AH_CODE,
//    MCH_CODE;
//
//    private String tableName;
//    private List<String> fieldNames;
//
//    private boolean isVectorIndexCreated;
//
//    private boolean isJoinRequired;
//
//    private String  joinWithTableName;
//
//    ProductSearchProperty(String tableName, List<String> fieldNames, boolean isVectorIndexCreated, boolean isJoinRequired, String joinWithTableName) {
//        this(tableName, fieldNames, isVectorIndexCreated, isJoinRequired);
//        this.joinWithTableName = joinWithTableName;
//
//    }
//
//    ProductSearchProperty(String tableName, List<String> fieldNames, boolean isVectorIndexCreated, boolean isJoinRequired) {
//        this.tableName = tableName;
//        this.fieldNames = fieldNames;
//        this.isVectorIndexCreated = isVectorIndexCreated;
//        this.isJoinRequired = isJoinRequired;
//
//    }
//
//}
