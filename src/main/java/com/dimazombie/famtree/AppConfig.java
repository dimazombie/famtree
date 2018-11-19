package com.dimazombie.famtree;

import org.glassfish.jersey.server.ResourceConfig;

public class AppConfig extends ResourceConfig {
    public AppConfig() {
        packages(true,"com.dimazombie.famtree");
    }
}
