package com.product.search.store;

import com.product.search.entity.Product;
import com.product.search.enums.ProductSearchProperty;
import com.product.search.model.request.ProductSearchCondition;
import com.product.search.model.request.ProductSearchRequest;
import com.product.search.repository.ProductRepository;
import com.product.search.util.ProductSpecifications;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import com.product.search.entity.*;

@RequiredArgsConstructor
@Service
public class ProductStore {

    private final ProductRepository productRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public Product getByLiam(String liam) {
        return productRepository.findOne(ProductSpecifications.withLiam(liam)).orElseGet(null);
    }


    public Page<Product> searchProducts(ProductSearchRequest productSearchRequest) {
        Specification<Product> specification = null;
        for (ProductSearchCondition condition : productSearchRequest.getConditions()) {
            if (specification == null) {
                specification = ProductSpecifications.with(condition.getProperty().getColumnName(), condition.getProperty().getOperator(condition.getOperator()), condition.getValue());
            } else {
                specification = specification.
                        and(ProductSpecifications.with(condition.getProperty().getColumnName(), condition.getProperty().getOperator(condition.getOperator()), condition.getValue()));
            }
        }
        return productRepository.findAll(specification, Pageable.ofSize(50));
    }

    public List<Product> searchProductsWithQuery(ProductSearchRequest productSearchRequest) {
//        JPAQuery<Product> query = jpaQueryFactory.selectFrom(QProduct.product);
//        query.where(QProduct.product.id.("20160441_EA"))
//        for (ProductSearchCondition condition : productSearchRequest.getConditions()) {
//
//        }
//
//        return List.of(
//                .where(QProduct.product.liam.eq("20160441_EA"))
//                .fetchOne());
//
//        try {
//            Query query = entityManager.createNativeQuery(ProductSearchQueryBuilder.of(productSearchRequest)
//                    .build(), Product.class);
//            return (List<Product>) query.getResultList();
//        } catch (Exception e) {
//            return List.of();
//        }

        return List.of();
    }
}
