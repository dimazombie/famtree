package com.dimazombie.famtree.rest;

import com.dimazombie.famtree.model.User;
import com.dimazombie.famtree.model.UserRepository;
import com.dimazombie.famtree.util.KeyGenerator;
import com.dimazombie.famtree.util.PasswordUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static javax.ws.rs.core.Response.Status.FORBIDDEN;

@Path("/authentication")
public class AuthResource {
    private Logger logger = LoggerFactory.getLogger(AuthResource.class);

    @Context UriInfo uriInfo;

    @Inject KeyGenerator keyGenerator;
    @Inject UserRepository repo;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@FormParam("login") String login,
                                     @FormParam("password") String password) {

        logger.debug("try to auth with: " + login + "/" + password);
        try {
            authenticate(login, password);

            String token = issueToken(login);

            logger.debug("token: " + token);

            return Response.ok(token).build();
            //TODO: to send token in header, to add constants class
            //return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();
        } catch (Exception e) {
            return Response.status(FORBIDDEN).build();
        }
    }

    private void authenticate(String login, String password) throws Exception {
        User user = repo.findByLoginPassword(login, PasswordUtils.digestPassword(password));

        if (user == null)
            throw new SecurityException("Invalid login/password");
    }

    private String issueToken(String login) {
        Key key = keyGenerator.generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        logger.debug("#### generating token for a key : " + jwtToken + " - " + key);
        return jwtToken;
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}