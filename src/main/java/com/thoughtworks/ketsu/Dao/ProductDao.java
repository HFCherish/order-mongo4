package com.thoughtworks.ketsu.Dao;

import com.thoughtworks.ketsu.domain.products.Product;
import com.thoughtworks.ketsu.infrastructure.mongo.mappers.ProductMapper;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import javax.inject.Inject;
import java.util.Map;

import static org.jongo.Oid.withOid;

public class ProductDao implements ProductMapper {

    private final MongoCollection prodCollection;

    @Inject
    public ProductDao(Jongo jongo) {
        prodCollection = jongo.getCollection("products");
    }

    @Override
    public Product save(Map<String, Object> info) {
        prodCollection.insert(info);
        return prodCollection.findOne().as(Product.class);
    }

    @Override
    public Product findById(String prodId) {
        return prodCollection.findOne(withOid(prodId)).as(Product.class);
    }
}
