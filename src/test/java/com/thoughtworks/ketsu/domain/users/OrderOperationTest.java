package com.thoughtworks.ketsu.domain.users;

import com.thoughtworks.ketsu.domain.products.Product;
import com.thoughtworks.ketsu.domain.products.ProductRepository;
import com.thoughtworks.ketsu.support.DatabaseTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.Optional;

import static com.thoughtworks.ketsu.support.TestHelper.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(DatabaseTestRunner.class)
public class OrderOperationTest {
    @Inject
    UserRepository userRepository;
    @Inject
    ProductRepository productRepository;
    private User user;
    private Product product;

    @Before
    public void setUp() throws Exception {
        user = userRepository.save(userJsonForTest(USER_NAME));
        product = productRepository.save(productJsonForTest());
    }

    @Test
    public void should_save_and_get_order() {
        Order save = user.placeOrder(orderJsonForTest(product.getId()));
        Optional<Order> fetched = user.findOrderById("");

        assertThat(fetched.isPresent(), is(true));
    }
}