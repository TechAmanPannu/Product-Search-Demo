package com.product.search.store;

import com.product.search.entity.Product;
import com.product.search.enums.ProductSearchOperator;
import com.product.search.enums.ProductSearchProperty;
import com.product.search.enums.ProductSearchQueryType;
import com.product.search.model.request.ProductSearchCondition;
import com.product.search.model.request.ProductSearchRequest;
import com.product.search.repository.ProductRepository;
import com.product.search.util.ProductSpecifications;
import com.product.search.util.query.QueryBuilder;
import com.product.search.util.query.SubqueryWhereClause;
import com.product.search.util.query.WhereClause;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;



@RequiredArgsConstructor
@Service
public class ProductStore {

    private final ProductRepository productRepository;
    private final EntityManager entityManager;

    private static final String[] QUERY_COLUMNS = {"id", "liam", "ah_code", "mch_code", "brand_code", "enrichment", "data", "inserted_at", "updated_at", "category_id", "status", "variant_data"};

    private static final String[] JOIN_QUERY_COLUMNS = {"products.id", "products.liam", "products.ah_code","products.mch_code", "products.brand_code", "products.enrichment", "products.data","products.inserted_at", "products.updated_at", "products.category_id", "products.status", "products.variant_data"};

    private static final String PRODUCT_TABLE_NAME = "products";

    private static final String CATEGORY_TABLE_NAME = "categories";

    private static final String ORDER_BY = "ASC";

    private static final String ORDER_BY_AND_NEXT_PAGE_PROPERTY = "id";

    private static final String PERFORMANCE_FENCE_OFFSET_VALUE = "0";

    private static final String PRODUCTS_AND_CATEGORIES_ON_JOIN_EXP = "products.category_id = categories.id";

    private static final String JOIN_QUERY_NEXT_PAGE_PROPERTY = "products.id";

    public Product getByLiam(String liam) {
        return productRepository.findOne(ProductSpecifications.withLiam(liam)).orElseGet(null);
    }


    public List<Product> searchProducts(ProductSearchRequest productSearchRequest, Integer limit, String nextPageCursor) {
        if (productSearchRequest == null || productSearchRequest.getConditions() == null || productSearchRequest.getConditions().isEmpty()) {
            return List.of();
        }
        try {
            String searchQuery = getSearchQuery(productSearchRequest, limit, nextPageCursor);
            System.out.println("SEARCH QUERY : "+searchQuery);
            return entityManager.createNativeQuery(searchQuery, Product.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    private String getSearchQuery(ProductSearchRequest productSearchRequest, Integer limit, String nextPageCursor) {
        QueryBuilder queryBuilder = new QueryBuilder(PRODUCT_TABLE_NAME, QUERY_COLUMNS);
        ProductSearchQueryType queryType = ProductSearchProperty.getSearchQueryType(productSearchRequest.getConditions());
        System.out.println("QueryType : "+queryType);
        switch (queryType) {
            case JOIN_QUERY:
                return getJoinQuery(productSearchRequest, limit, nextPageCursor, queryBuilder);
            case SUB_QUERY:
                return getSubQuery(productSearchRequest, limit, nextPageCursor, queryBuilder);
            default:
                return getBasicQuery(productSearchRequest, limit, nextPageCursor, queryBuilder);
        }
    }

    private String getJoinQuery(ProductSearchRequest productSearchRequest, Integer limit, String nextPageCursor, QueryBuilder queryBuilder) {
        List<ProductSearchCondition> conditions = productSearchRequest.getConditions();
        SubqueryWhereClause whereClause = queryBuilder.joinSubquery(CATEGORY_TABLE_NAME, PRODUCTS_AND_CATEGORIES_ON_JOIN_EXP, JOIN_QUERY_COLUMNS).where();
        whereClause = productSearchRequest.isAllConditionsMatch() ? getAllConditionsMatched(whereClause, conditions, true) : getAllConditionsNonMatched(whereClause, conditions, true);
        return whereClause.build().offset(PERFORMANCE_FENCE_OFFSET_VALUE).nextPage(JOIN_QUERY_NEXT_PAGE_PROPERTY, nextPageCursor).build().sortBy(ORDER_BY_AND_NEXT_PAGE_PROPERTY, ORDER_BY).limit(limit.toString()).get();
    }

    private String getSubQuery(ProductSearchRequest productSearchRequest, Integer limit, String nextPageCursor, QueryBuilder queryBuilder) {
        List<ProductSearchCondition> conditions = productSearchRequest.getConditions();
        SubqueryWhereClause whereClause = queryBuilder.subquery().where();
        whereClause = productSearchRequest.isAllConditionsMatch() ? getAllConditionsMatched(whereClause, conditions) : getAllConditionsNonMatched(whereClause, conditions);
        return whereClause.build().offset(PERFORMANCE_FENCE_OFFSET_VALUE).nextPage(ORDER_BY_AND_NEXT_PAGE_PROPERTY, nextPageCursor).build().sortBy(ORDER_BY_AND_NEXT_PAGE_PROPERTY, ORDER_BY).limit(limit.toString()).get();
    }


    private String getBasicQuery(ProductSearchRequest productSearchRequest, Integer limit, String nextPageCursor, QueryBuilder queryBuilder) {
        List<ProductSearchCondition> conditions = productSearchRequest.getConditions();
        WhereClause whereClause = queryBuilder.where();
        whereClause = productSearchRequest.isAllConditionsMatch() ? getAllConditionsMatched(whereClause, conditions) : getAllConditionsNonMatched(whereClause, conditions);
        return whereClause.build().nextPage(ORDER_BY_AND_NEXT_PAGE_PROPERTY, nextPageCursor).sortBy(ORDER_BY_AND_NEXT_PAGE_PROPERTY, ORDER_BY).limit(limit.toString()).get();
    }


    private SubqueryWhereClause getAllConditionsMatched(SubqueryWhereClause whereClause, List<ProductSearchCondition> conditions) {
        return getAllConditionsMatched(whereClause, conditions, false);
    }

    private SubqueryWhereClause getAllConditionsNonMatched(SubqueryWhereClause whereClause, List<ProductSearchCondition> conditions) {
        return getAllConditionsNonMatched(whereClause, conditions, false);
    }

    private SubqueryWhereClause getAllConditionsMatched(SubqueryWhereClause whereClause, List<ProductSearchCondition> conditions, boolean isJoinQuery) {
        for (int i = 0; i < conditions.size(); i++) {
            ProductSearchCondition condition = conditions.get(i);
            ProductSearchOperator productSearchOperator = condition.getOperator();
            ProductSearchProperty productSearchProperty = condition.getProperty();
            String value = condition.getValue();
            if (productSearchProperty == ProductSearchProperty.DIETARY) {
                whereClause = whereClause.andProductDietary(productSearchProperty.DIETARY.getOperator(productSearchOperator), List.of(value));
            } else {
                whereClause = whereClause.and(productSearchProperty.getColumnName(productSearchOperator, isJoinQuery), productSearchProperty.getOperator(productSearchOperator), productSearchProperty.getValue(productSearchOperator, value));
            }
        }
        return whereClause;
    }

    private SubqueryWhereClause getAllConditionsNonMatched(SubqueryWhereClause whereClause, List<ProductSearchCondition> conditions, boolean isJoinedQuery) {
        for (int i = 0; i < conditions.size(); i++) {
            ProductSearchCondition condition = conditions.get(i);
            ProductSearchOperator productSearchOperator = condition.getOperator();
            ProductSearchProperty productSearchProperty = condition.getProperty();
            String value = condition.getValue();
            if (productSearchProperty == ProductSearchProperty.DIETARY) {
                whereClause = whereClause.orProductDietary(ProductSearchProperty.DIETARY.getOperator(productSearchOperator), List.of(value));
            } else {
                whereClause = whereClause.or(productSearchProperty.getColumnName(productSearchOperator, isJoinedQuery), productSearchProperty.getOperator(productSearchOperator), productSearchProperty.getValue(productSearchOperator, value));
            }
        }

        return whereClause;
    }

    private WhereClause getAllConditionsMatched(WhereClause whereClause, List<ProductSearchCondition> conditions) {
        for (int i = 0; i < conditions.size(); i++) {
            ProductSearchCondition condition = conditions.get(i);
            ProductSearchOperator productSearchOperator = condition.getOperator();
            ProductSearchProperty productSearchProperty = condition.getProperty();
            String value = condition.getValue();
            whereClause = whereClause.and(productSearchProperty.getColumnName(productSearchOperator), productSearchProperty.getOperator(productSearchOperator), productSearchProperty.getValue(productSearchOperator, value));
        }
        return whereClause;
    }

    private WhereClause getAllConditionsNonMatched(WhereClause whereClause, List<ProductSearchCondition> conditions) {
        for (int i = 0; i < conditions.size(); i++) {
            ProductSearchCondition condition = conditions.get(i);
            ProductSearchOperator productSearchOperator = condition.getOperator();
            ProductSearchProperty productSearchProperty = condition.getProperty();
            String value = condition.getValue();
            whereClause = whereClause.or(productSearchProperty.getColumnName(productSearchOperator), productSearchProperty.getOperator(productSearchOperator), productSearchProperty.getValue(productSearchOperator, value));
        }
        return whereClause;
    }
}


