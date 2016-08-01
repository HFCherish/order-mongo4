package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.products.Product;
import com.thoughtworks.ketsu.domain.products.ProductRepository;
import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.ApiTestRunner;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import java.util.Map;

import static com.thoughtworks.ketsu.support.TestHelper.productJsonForTest;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(ApiTestRunner.class)
public class ProductApiTest extends ApiSupport{
    private String baseUrl = "/products";
    @Inject
    ProductRepository productRepository;

    @Test
    public void should_create_product() {
        Response response = post(baseUrl, productJsonForTest());

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation().toString(), containsString(baseUrl));
        assertThat(response.getLocation().toString().matches(".*/[a-zA-Z\\d]+$"), is(true));

    }

    @Test
    public void should_400_when_create_given_incomplete_product() {
        Map<String, Object> info = productJsonForTest();
        info.remove("name");

        Response response = post(baseUrl, info);

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_get_some_product() {
        Map<String, Object> info = productJsonForTest();
        Product save = productRepository.save(info);

        Response response = get(baseUrl + "/" + save.getId());

        assertThat(response.getStatus(), is(200));
        Map fetchedInfo = response.readEntity(Map.class);
        assertThat(fetchedInfo.get("uri").toString(), containsString(baseUrl + "/" + save.getId()));
        assertThat(fetchedInfo.get("name"), is(info.get("name")));
        assertThat(fetchedInfo.get("description"), is(info.get("description")));
        assertThat(fetchedInfo.get("price"), is(info.get("price")));
        assertThat(fetchedInfo.get("_id"), is(save.getId()));
    }

    @Test
    public void should_404_when_get_given_not_exists() {
        Map<String, Object> info = productJsonForTest();
        Product save = productRepository.save(info);

        Response response = get(baseUrl + "/" + new ObjectId());

        assertThat(response.getStatus(), is(404));

    }
}