package com.thoughtworks.ketsu.domain.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;
import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Order implements Record {
    @MongoId
    @MongoObjectId
    String id;
    @JsonProperty("user_id")
    String userId;
    String name;
    String address;
    String phone;
    @JsonProperty("order_items")
    List<OrderItem> orderItems;

    public String getId() {
        return id;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap() {{
            put("uri", routes.orderUrl(userId, id));
            put("name", name);
            put("address", address);
            put("phone", phone);
            put("total_price", getTotalPrice());
            put("created_at", new ObjectId(id).getDate().toString());
        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        Map<String, Object> res = toRefJson(routes);
        res.put("order_items", orderItems.stream().map(orderItem -> orderItem.toJson(routes)).collect(Collectors.toList()));
        return res;
    }

    public double getTotalPrice() {
        double res = 0;
        for (OrderItem orderItem : orderItems) {
            res += orderItem.amount * orderItem.quantity;
        }
        return res;
    }

    public Payment pay(Map<String, Object> info) {
        return null;
    }

    public Optional<Payment> getPayment() {
        return Optional.ofNullable(new Payment());
    }
}
