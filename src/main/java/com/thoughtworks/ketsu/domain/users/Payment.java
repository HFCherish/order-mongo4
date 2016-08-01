package com.thoughtworks.ketsu.domain.users;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import java.util.HashMap;
import java.util.Map;

public class Payment implements Record{
    @JsonProperty("amount")
    double amount;
    @JsonProperty("pay_type")
    PayType payType;

    @JacksonInject("order")
    Order order;

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap() {{
            put("order_uri", routes.orderUrl(order.userId, order.id));
            put("uri", routes.paymentUrl(order.userId, order.id));
            put("pay_type", payType);
            put("amount", amount);
        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }
}
