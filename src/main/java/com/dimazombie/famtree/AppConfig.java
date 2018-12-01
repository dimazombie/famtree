package com.dimazombie.famtree;

import com.dimazombie.famtree.model.JDBCNodeRepository;
import com.dimazombie.famtree.model.NodeRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

public class AppConfig extends ResourceConfig {
    public AppConfig() {
        packages(true,"com.dimazombie.famtree");
        register(new AbstractBinder(){
            @Override
            protected void configure() {
                bind(JDBCNodeRepository.class).to(NodeRepository.class);
            }
        });
    }
}
