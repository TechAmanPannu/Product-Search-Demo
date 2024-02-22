package com.product.search.model.request;

import java.util.HashMap;
import java.util.List;

public class ProductSearchValueModel extends HashMap<String, List<String>> {

    public ProductSearchValueModel() {
        super(new HashMap<>());
    }

    public List<String> getAndValues() {
        return this.get("AND");
    }

    public List<String> getOrValues() {
        return this.get("OR");
    }
}
