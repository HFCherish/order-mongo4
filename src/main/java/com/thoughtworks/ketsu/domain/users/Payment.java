package com.thoughtworks.ketsu.domain.users;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Payment {
    @JsonProperty("amount")
    double amount;
    @JsonProperty("pay_type")
    PayType payType;

    @JacksonInject("order")
    Order order;
}
