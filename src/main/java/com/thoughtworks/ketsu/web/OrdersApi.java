package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.users.User;
import com.thoughtworks.ketsu.web.jersey.Routes;
import com.thoughtworks.ketsu.web.validators.OrderValidator;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

public class OrdersApi {
    private User user;

    public OrdersApi(User user) {
        this.user = user;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response placeOrder(Map<String, Object> info,
                               @Context Routes routes,
                               @Context OrderValidator orderValidator) {

        orderValidator.validate(info);

        return Response.created(routes.orderUrl(user.getId(), user.placeOrder(info).getId())).build();
    }

    @Path("{id}")
    public OrderApi getOrder(@PathParam("id") String id) {
        return user.findOrderById(id)
                .map(OrderApi::new)
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }
}