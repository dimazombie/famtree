package com.dimazombie.famtree;

import com.dimazombie.famtree.model.FileEntryRepository;
import com.dimazombie.famtree.model.JDBCFileEntryRepository;
import com.dimazombie.famtree.model.JDBCNodeRepository;
import com.dimazombie.famtree.model.NodeRepository;
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
            }
        });
        register(MultiPartFeature.class);
    }
}
