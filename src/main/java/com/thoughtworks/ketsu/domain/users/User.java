package com.thoughtworks.ketsu.domain.users;

import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class User implements Record {
    @MongoId
    @MongoObjectId
    String id;
    String name;

    public String getId() {
        return id;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap() {{
            put("uri", routes.userUrl(id));
            put("name", name);
            put("_id", id);
        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }

    public Order placeOrder(Map<String, Object> info) {
        return null;
    }

    public Optional<Order> findOrderById(String id) {
        return Optional.ofNullable(new Order());
    }
}
