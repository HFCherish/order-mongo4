package com.thoughtworks.ketsu.domain.products;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

public class Product {
    @MongoId
    @MongoObjectId
    String id;
    String name;
    String description;
    double price;

    public String getId() {
        return id;
    }
}
