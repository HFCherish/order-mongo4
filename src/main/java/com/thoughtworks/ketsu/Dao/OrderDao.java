package com.thoughtworks.ketsu.Dao;

import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import com.thoughtworks.ketsu.domain.users.Order;
import com.thoughtworks.ketsu.domain.users.Payment;
import com.thoughtworks.ketsu.infrastructure.mongo.mappers.OrderMapper;
import com.thoughtworks.ketsu.util.SafeInjector;
import org.joda.time.DateTime;
import org.jongo.*;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.jongo.Oid.withOid;

public class OrderDao implements OrderMapper {
    private final MongoCollection orderCollection;

    @Inject
    ProductDao productDao;

    @Inject
    public OrderDao(Jongo jongo) {
        orderCollection = jongo.getCollection("orders");
    }

    @Override
    public Order save(Map<String, Object> info, String userId) {
        info.put("user_id", userId);
        for(Map item: (List<Map>) info.get("order_items")) {
            item.put("amount", productDao.getPriceOf(item.get("product_id").toString()));
        }
        orderCollection.insert(info);
        return SafeInjector.injectMembers(orderCollection.findOne().as(Order.class));
    }

    @Override
    public Order findById(String id) {
        return SafeInjector.injectMembers(orderCollection.findOne(withOid(id)).as(Order.class));
    }

    @Override
    public List<Order> findAllOf(String userId) {
        List<Order> orders = new ArrayList<>();
        MongoCursor<Order> cursor = orderCollection.find("{user_id:#}", userId).as(Order.class);
        while(cursor.hasNext()) {
            orders.add(SafeInjector.injectMembers(cursor.next()));
        }
        return orders;
    }

    @Override
    public Payment getPaymentOf(String orderId) {
        FindOne one = orderCollection.findOne(withOid(orderId));
        return getPayment(one);
    }

    @Override
    public Payment pay(Map<String, Object> info, String orderId) {
        info.put("created_at", new DateTime().toString());
        orderCollection.update(withOid(orderId)).with("{$set: {payment:#}}", info);
        return getPaymentOf(orderId);
    }

    private Payment getPayment(FindOne one) {
        Order order = one.as(Order.class);
        return one.map(new ResultHandler<Payment>() {
            @Override
            public Payment map(DBObject result) {
                Object paymentInfo = result.get("payment");
                if(paymentInfo == null) return null;
                try {
                    return new ObjectMapper().readerFor(Payment.class).with(new InjectableValues.Std().addValue("order", order)).readValue(paymentInfo.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }
}
