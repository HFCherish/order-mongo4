package com.thoughtworks.ketsu.web.validators;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OrderValidator extends NullFieldValidator {
    public boolean validate(Map<String, Object> info) {
        List<Map> nullFields = getNullFields(Arrays.asList("name", "address", "phone"), info);
        if(nullFields.size() != 0 ) {
            throw new IllegalArgumentException(nullFields.toString());
        }

        return true;
    }
}
