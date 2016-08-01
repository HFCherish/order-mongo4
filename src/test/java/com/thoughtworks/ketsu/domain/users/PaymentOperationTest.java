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
public class PaymentOperationTest {
    @Inject
    UserRepository userRepository;
    @Inject
    ProductRepository productRepository;
    private User user;
    private Product product;
    private Order order;

    @Before
    public void setUp() throws Exception {
        user = userRepository.save(userJsonForTest(USER_NAME));
        product = productRepository.save(productJsonForTest());
        order = user.placeOrder(orderJsonForTest(product.getId()));
    }

    @Test
    public void should_pay_and_get_payment() {
        Payment pay = order.pay(paymentJsonForTest());
        Optional<Payment> fetched = order.getPayment();

        assertThat(fetched.isPresent(), is(true));
        assertThat(fetched.get().order.getId(), is(pay.order.getId()));

    }
}