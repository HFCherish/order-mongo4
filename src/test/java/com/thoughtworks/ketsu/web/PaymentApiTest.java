package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.products.Product;
import com.thoughtworks.ketsu.domain.products.ProductRepository;
import com.thoughtworks.ketsu.domain.users.Order;
import com.thoughtworks.ketsu.domain.users.Payment;
import com.thoughtworks.ketsu.domain.users.User;
import com.thoughtworks.ketsu.domain.users.UserRepository;
import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.ApiTestRunner;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import java.util.Map;

import static com.thoughtworks.ketsu.support.TestHelper.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(ApiTestRunner.class)
public class PaymentApiTest extends ApiSupport{
    @Inject
    UserRepository userRepository;
    @Inject
    ProductRepository productRepository;
    private User user;
    private Product product;
    private String baseUrl;
    private Order order;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        user = userRepository.save(userJsonForTest(USER_NAME));
        product = productRepository.save(productJsonForTest());
        order = user.placeOrder(orderJsonForTest(product.getId()));
        baseUrl = "/users/" + user.getId() + "/orders/" + order.getId() + "/payment";
    }

    @Test
    public void should_pay() {
        Response response = post(baseUrl, paymentJsonForTest());

        assertThat(response.getStatus(), is(201));
    }

    @Test
    public void should_400_when_pay_given_incomplete_input() {
        Map<String, Object> info = paymentJsonForTest();
        info.remove("pay_type");
        Response response = post(baseUrl, info);

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_get_payment() {
        Map<String, Object> info = paymentJsonForTest();
        Payment pay = order.pay(info);
        Response response = get(baseUrl);

        assertThat(response.getStatus(), is(200));
        Map fetched = response.readEntity(Map.class);
        assertThat(fetched.get("order_uri").toString(), containsString("/users/" + user.getId() + "/orders/" + order.getId()));
        assertThat(fetched.get("uri").toString(), containsString(baseUrl));
        assertThat(fetched.get("pay_type"), is(info.get("pay_type")));
        assertThat(fetched.get("amount"), is(info.get("amount")));
        assertThat(fetched.get("created_at").toString(), is(notNullValue()));
    }

    @Test
    public void should_404_when_get_payment_given_not_pay() {
        Response response = get(baseUrl);

        assertThat(response.getStatus(), is(404));

    }
}