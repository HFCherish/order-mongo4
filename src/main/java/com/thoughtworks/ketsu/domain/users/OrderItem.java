package com.thoughtworks.ketsu.domain.users;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderItem {
    @JsonProperty("product_id")
    String prodId;
    int quantity;
}
