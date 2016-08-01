package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.products.Product;
import com.thoughtworks.ketsu.domain.products.ProductRepository;
import com.thoughtworks.ketsu.domain.users.User;
import com.thoughtworks.ketsu.domain.users.UserRepository;
import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.ApiTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import static com.thoughtworks.ketsu.support.TestHelper.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(ApiTestRunner.class)
public class OrdersApiTest extends ApiSupport{
    @Inject
    UserRepository userRepository;
    @Inject
    ProductRepository productRepository;
    private User user;
    private Product product;
    private String baseUrl;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        user = userRepository.save(userJsonForTest(USER_NAME));
        product = productRepository.save(productJsonForTest());
        baseUrl = "/users/" + user.getId() + "/orders";
    }

    @Test
    public void should_create_order() {
        Response response = post(baseUrl, orderJsonForTest(product.getId()));

        assertThat(response.getStatus(), is(201));

    }
}