package com.dimazombie.famtree;

import com.dimazombie.famtree.web.AuthenticationFilter;
import org.apache.cxf.common.logging.Slf4jLogger;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class AppConfig extends ResourceConfig {
    public AppConfig() {
        packages("com.dimazombie.famtree.rest");
        register(AuthenticationFilter.class);
        register(new LoggingFeature(
                new Slf4jLogger(LoggingFeature.DEFAULT_LOGGER_NAME, null)
        ));
    }
}
