package com.thoughtworks.ketsu.support;

import java.util.HashMap;
import java.util.Map;

public class TestHelper {

    public static final String USER_NAME = "Petrina";

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
}
