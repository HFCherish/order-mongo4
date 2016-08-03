package com.thoughtworks.ketsu.domain.products;

import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.HashMap;
import java.util.Map;

public class Product implements Record{
    @MongoId
    @MongoObjectId
    String id;
    String name;
    String description;
    double price;

    public String getId() {
        return id;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap() {{
            put("uri", routes.productUrl(id));
            put("name", name);
            put("description", description);
            put("price", price);
            put("id", id);
        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }
}
