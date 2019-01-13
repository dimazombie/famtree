package com.dimazombie.famtree.rest;

import com.dimazombie.famtree.model.User;
import com.dimazombie.famtree.web.Secured;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;

@Path("users")
@Produces("application/json")
public class UserResource {

    @Context ContainerRequestContext context;

    @GET
    @Path("/current")
    @Secured
    public User getCurrentUser() {
        return (User) context.getProperty(User.PROPERTY_NAME);
    }
}
