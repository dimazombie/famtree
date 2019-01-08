package com.dimazombie.famtree;

import com.dimazombie.famtree.model.*;
import com.dimazombie.famtree.util.KeyGenerator;
import com.dimazombie.famtree.util.SimpleKeyGenerator;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class AppConfig extends ResourceConfig {
    public AppConfig() {
        packages(true,"com.dimazombie.famtree");
        register(new AbstractBinder(){
            @Override
            protected void configure() {
                bind(JDBCNodeRepository.class).to(NodeRepository.class);
                bind(JDBCFileEntryRepository.class).to(FileEntryRepository.class);
                bind(JDBCUserRepository.class).to(UserRepository.class);
                bind(SimpleKeyGenerator.class).to(KeyGenerator.class);
            }
        });
        register(MultiPartFeature.class);
    }
}
