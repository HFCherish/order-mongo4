package com.thoughtworks.ketsu.domain.users;

import com.thoughtworks.ketsu.infrastructure.mongo.mappers.OrderMapper;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class User implements Record {
    @MongoId
    @MongoObjectId
    String id;
    String name;

    @Inject
    OrderMapper orderMapper;

    public String getId() {
        return id;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap() {{
            put("uri", routes.userUrl(id));
            put("name", name);
            put("id", id);
        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }

    public Order placeOrder(Map<String, Object> info) {
        return orderMapper.save(info, id);
    }

    public Optional<Order> findOrderById(String id) {
        return Optional.ofNullable(orderMapper.findById(id));
    }

    public List<Order> findAllOrders() {
        return orderMapper.findAllOf(id);
    }
}
