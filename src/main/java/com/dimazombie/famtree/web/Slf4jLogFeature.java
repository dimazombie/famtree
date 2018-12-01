package com.dimazombie.famtree.web;

import org.apache.cxf.common.logging.Slf4jLogger;
import org.glassfish.jersey.logging.LoggingFeature;
import javax.ws.rs.ext.Provider;

@Provider
public class Slf4jLogFeature extends LoggingFeature {
    public Slf4jLogFeature(){
        super(new Slf4jLogger(LoggingFeature.DEFAULT_LOGGER_NAME, null));
    }
}
