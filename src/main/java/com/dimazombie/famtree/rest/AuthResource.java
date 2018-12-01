package com.dimazombie.famtree.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/authentication")
public class AuthResource {
    private Logger logger = LoggerFactory.getLogger(AuthResource.class);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@FormParam("username") String username,
                                     @FormParam("password") String password) {
        logger.debug("try to auth");
        try {
            authenticate(username, password);

            String token = issueToken(username);

            return Response.ok(token).build();
        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    private void authenticate(String username, String password) throws Exception {
        if(!username.equals("test") || !password.equals("test")) {
            throw new Exception();
        }

    }

    private String issueToken(String username) {
        return "secret_token";
    }
}