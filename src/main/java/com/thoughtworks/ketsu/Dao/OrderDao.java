package com.thoughtworks.ketsu.Dao;

import com.thoughtworks.ketsu.domain.users.Order;
import com.thoughtworks.ketsu.infrastructure.mongo.mappers.OrderMapper;
import com.thoughtworks.ketsu.util.SafeInjector;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import javax.inject.Inject;
import java.util.Map;

import static org.jongo.Oid.withOid;

public class OrderDao implements OrderMapper {
    private final MongoCollection orderCollection;

    @Inject
    public OrderDao(Jongo jongo) {
        orderCollection = jongo.getCollection("orders");
    }

    @Override
    public Order save(Map<String, Object> info, String userId) {
        info.put("user_id", userId);
        orderCollection.insert(info);
        return SafeInjector.injectMembers(orderCollection.findOne().as(Order.class));
    }

    @Override
    public Order findById(String id) {
        return SafeInjector.injectMembers(orderCollection.findOne(withOid(id)).as(Order.class));
    }
}
