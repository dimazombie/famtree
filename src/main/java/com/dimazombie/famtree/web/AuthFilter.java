package com.dimazombie.famtree.web;

import com.dimazombie.famtree.util.KeyGenerator;
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
                validateToken(token);
            } catch (Exception e) {
                logger.error("", e);
                requestContext.abortWith(ACCESS_DENIED);
                return;
            }
        }
    }

    private void validateToken(String token) throws Exception {
        Key key = keyGenerator.generateKey();
        Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        logger.info("#### valid token : " + token);
    }
}
