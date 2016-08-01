package com.thoughtworks.ketsu.domain.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.List;

public class Order {
    @MongoId
    @MongoObjectId
    String id;
    @JsonProperty("user_id")
    String userId;
    String name;
    String address;
    String phone;
    List<OrderItem> orderItems;

    public String getId() {
        return id;
    }
}
