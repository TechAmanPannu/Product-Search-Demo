package com.product.search.service;

import com.product.search.entity.Product;
import com.product.search.model.request.ProductSearchRequest;
import com.product.search.model.response.ProductResponseModel;

import java.util.List;

public interface ProductService {

    ProductResponseModel getByLiam(String liam);

    List<ProductResponseModel> search(ProductSearchRequest productSearchRequest, Integer limit, String nextPageCursor);
}
