package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.products.Product;
import com.thoughtworks.ketsu.domain.products.ProductRepository;
import com.thoughtworks.ketsu.domain.users.Order;
import com.thoughtworks.ketsu.domain.users.User;
import com.thoughtworks.ketsu.domain.users.UserRepository;
import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.ApiTestRunner;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

import static com.thoughtworks.ketsu.support.TestHelper.*;
import static org.hamcrest.CoreMatchers.containsString;
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
        assertThat(response.getLocation().toString(), containsString(baseUrl));
        assertThat(response.getLocation().toString().matches(".*/[a-zA-Z\\d]+$"), is(true));

    }

    @Test
    public void should_400_when_create_given_incomplete_order_info() {
        Map<String, Object> info = orderJsonForTest(product.getId());
        info.remove("name");

        Response response = post(baseUrl, info);

        assertThat(response.getStatus(), is(400));

    }

    @Test
    public void should_400_when_create_given_invalid_order_item_info() {
        Map<String, Object> info = orderJsonForTest(new ObjectId().toString());

        Response response = post(baseUrl, info);

        assertThat(response.getStatus(), is(400));

    }

    @Test
    public void should_get_some_order() {
        Map<String, Object> info = orderJsonForTest(product.getId());
        Order save = user.placeOrder(info);

        Response response = get(baseUrl + "/" + save.getId());

        assertThat(response.getStatus(), is(200));
        Map fetched = response.readEntity(Map.class);
        assertThat(fetched.get("uri").toString(), containsString(baseUrl + "/" + save.getId()));
        assertThat(fetched.get("name"), is(info.get("name")));
        assertThat(fetched.get("address"), is(info.get("address")));
        assertThat(fetched.get("phone"), is(info.get("phone")));
        assertThat((double)fetched.get("total_price"), is(PRODUCT_PRICE * PRODUCT_QUANTITY));
        assertThat(fetched.get("created_at"), is(new ObjectId(save.getId()).getDate().toString()));

        List<Map> items = (List)fetched.get("order_items");
        assertThat(items.size(), is(1));
        assertThat(items.get(0).get("product_id"), is(product.getId()));
        assertThat(items.get(0).get("quantity"), is(PRODUCT_QUANTITY));
        assertThat((double)items.get(0).get("amount"), is(PRODUCT_PRICE));
        assertThat(items.get(0).get("uri").toString(), containsString("/products/" + product.getId()));
    }

    @Test
    public void should_return_404_when_get_some_order_given_not_exist() {
        Map<String, Object> info = orderJsonForTest(product.getId());
        Order save = user.placeOrder(info);

        Response response = get(baseUrl + "/" + new ObjectId());

        assertThat(response.getStatus(), is(404));
    }
}