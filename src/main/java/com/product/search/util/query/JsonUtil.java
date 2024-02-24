package com.product.search.util.query;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class JsonUtil {

    public static Map<String, Object> getMapFromJson(String jsonString) {
        if(jsonString == null || jsonString.isEmpty()) {
            return null;
        }
        try {
            return new ObjectMapper().readValue(jsonString, new TypeReference<Map<String, Object>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
