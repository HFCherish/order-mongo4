package com.thoughtworks.ketsu.web.validators;

import com.thoughtworks.ketsu.domain.products.ProductRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OrderValidator extends NullFieldValidator {
    @Inject
    ProductRepository productRepository;

    public boolean validate(Map<String, Object> info) {
        List<Map> nullFields = getNullFields(info);
        if (nullFields.size() != 0) {
            throw new IllegalArgumentException(nullFields.toString());
        }

        return true;
    }

    List<Map> getNullFields(Map<String, Object> info) {
        List<Map> nullFields = getNullFields(Arrays.asList("name", "address", "phone", "order_items"), info);

        Object order_items = info.get("order_items");
        if (order_items != null) {
            for (Map item : (List<Map>) order_items) {
                nullFields.addAll(getNullFields(Arrays.asList("product_id", "quantity"), item));

                Object product_id = item.get("product_id");
                if(product_id !=null && !productRepository.findById(product_id.toString()).isPresent()) {
                    nullFields.add(errorItem(product_id.toString(), "product_id is invalid."));
                }
            }
        }
        return nullFields;
    }
}
