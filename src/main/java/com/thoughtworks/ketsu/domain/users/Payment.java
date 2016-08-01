package com.thoughtworks.ketsu.domain.users;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Payment {
    double amount;
    @JsonProperty("pay_type")
    PayType payType;

    Order order;
}
