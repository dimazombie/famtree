package com.dimazombie.famtree.rest;

import com.dimazombie.famtree.model.Node;
import com.dimazombie.famtree.model.NodeRepository;
import com.dimazombie.famtree.web.Secured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("node")
@Produces("application/json")
public class NodeResource {
    Logger logger = LoggerFactory.getLogger(NodeResource.class);

    @Inject NodeRepository repo;

    @GET
    @Path("111")
    public List<Node> getAllNodes() {
        return repo.getAllNodes();
    }

    @GET
    @Secured
    @Path("222")
    public List<Node> getAllNodesSecured() {
        return repo.getAllNodes();
    }
}
