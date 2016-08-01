package com.thoughtworks.ketsu.support;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestHelper {

    public static final String USER_NAME = "Petrina";
    public static final String INVALID_USER_NAME = "PetrinL;';a";

    public static Map<String, Object> productJsonForTest() {
        return new HashMap() {{
           put("name", "Imran");
           put("description", "teacher");
           put("price", 1.2);
        }};
    }

    public static Map<String, Object> userJsonForTest(String name) {
        return new HashMap() {{
            put("name", name);
        }};
    }

    public static Map<String, Object> orderJsonForTest(String prodId) {
        return new HashMap() {{
            put("name", "Petrina");
            put("address", "beijing");
            put("phone", "689067879");
            put("order_items", Arrays.asList(new HashMap() {{
                put("product_id", prodId);
                put("quantity", 2);
            }}));
        }};
    }
}
