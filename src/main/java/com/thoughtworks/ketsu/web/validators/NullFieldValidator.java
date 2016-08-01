package com.thoughtworks.ketsu.web.validators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NullFieldValidator {
    public boolean validate(List<String> toValidates, Map<String, Object> info) {
        List<Map> nullFields = getNullFields(toValidates, info);
        if (nullFields.size() != 0) {
            throw new IllegalArgumentException(nullFields.toString());
        }
        return true;
    }

    List<Map> getNullFields(List<String> toValidates, Map<String, Object> info) {
        List res = new ArrayList<>();

        for (String toValidate : toValidates) {
            if (info.get(toValidate) == null) {
                res.add(errorItem(toValidate, toValidate + " can not be empty."));
            }
        }

        return res;
    }

    public Map<String, Object> errorItem(String name, String message) {
        return new HashMap() {{
            put("field", name);
            put("message", message);
        }};
    }
}
