package com.dimazombie.famtree.web;

import com.dimazombie.famtree.model.User;
import com.dimazombie.famtree.model.UserRepository;
import com.dimazombie.famtree.util.KeyGenerator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;
import java.security.Key;
import java.util.List;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Provider
public class AuthFilter implements ContainerRequestFilter {
    private Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Context private ResourceInfo resourceInfo;

    @Inject KeyGenerator keyGenerator;

    @Inject UserRepository repo;

    private static final String AUTHENTICATION_SCHEME = "Bearer";
    private static final Response ACCESS_DENIED = Response.status(UNAUTHORIZED).build();

    @Override
    public void filter(ContainerRequestContext requestContext) {
        Method method = resourceInfo.getResourceMethod();
        if(method.isAnnotationPresent(Secured.class)) {
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();

            List<String> authorization = headers.get(AUTHORIZATION);

            if(authorization == null || authorization.isEmpty()) {
                logger.error("#### invalid authorization header : " + authorization);
                requestContext.abortWith(ACCESS_DENIED);
                return;
            }

            final String token = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
            logger.debug("token=" + token);

            try {
                Claims claims = parseClaims(token);
                String login = claims.getSubject();
                logger.debug("login=" + login);
                User user = repo.findByLogin(login);
                requestContext.setProperty(User.PROPERTY_NAME, user);
            } catch (Exception e) {
                logger.error("", e);
                requestContext.abortWith(ACCESS_DENIED);
                return;
            }
        }
    }

    private Claims parseClaims(String token) throws Exception {
        Key key = keyGenerator.generateKey();
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }
}
