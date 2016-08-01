package com.thoughtworks.ketsu.domain.users;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

public class User {
    @MongoId
    @MongoObjectId
    String id;
    String name;

    public String getId() {
        return id;
    }
}
