package com.dimazombie.famtree;

import com.dimazombie.famtree.model.NodeRepository;
import org.glassfish.jersey.internal.inject.AbstractBinder;

public class AppBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(NodeRepository.class).to(NodeRepository.class);
    }
}