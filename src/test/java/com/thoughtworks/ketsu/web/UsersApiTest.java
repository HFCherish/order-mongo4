package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.users.User;
import com.thoughtworks.ketsu.domain.users.UserRepository;
import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.ApiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import java.util.Map;

import static com.thoughtworks.ketsu.support.TestHelper.INVALID_USER_NAME;
import static com.thoughtworks.ketsu.support.TestHelper.USER_NAME;
import static com.thoughtworks.ketsu.support.TestHelper.userJsonForTest;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(ApiTestRunner.class)
public class UsersApiTest extends ApiSupport{
    private String baseUrl = "/users";
    @Inject
    UserRepository userRepository;

    @Test
    public void should_register_user() {
        Response response = post(baseUrl, userJsonForTest(USER_NAME));

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation().toString(), containsString(baseUrl));
        assertThat(response.getLocation().toString().matches(".*/[a-zA-Z\\d]+$"), is(true));

    }

    @Test
    public void should_400_when_name_invalid() {
        Response response = post(baseUrl, userJsonForTest(INVALID_USER_NAME));

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_get_user() {
        Map<String, Object> info = userJsonForTest(USER_NAME);
        User save = userRepository.save(info);

        Response response = get(baseUrl + "/" + save.getId());

        assertThat(response.getStatus(), is(200));
        Map fetchedInfo = response.readEntity(Map.class);
        assertThat(fetchedInfo.get("uri").toString(), containsString(baseUrl + "/" + save.getId()));
        assertThat(fetchedInfo.get("name"), is(info.get("name")));
        assertThat(fetchedInfo.get("_id"), is(save.getId()));
    }
}