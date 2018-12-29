package com.dimazombie.famtree.rest;

import com.dimazombie.famtree.model.Node;
import com.dimazombie.famtree.model.NodeRepository;
import com.dimazombie.famtree.web.Secured;
import org.apache.cxf.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

@Path("nodes")
@Produces("application/json")
public class NodeResource {
    Logger logger = LoggerFactory.getLogger(NodeResource.class);

    @Inject NodeRepository repo;

    @GET
    @Secured
    public List<Node> getAllNodes() {
        return repo.getAllNodes();
    }

    @POST
    @Secured
    public Node addNewNode(@FormParam("parentId") String parentId) {
        Node newNode = new Node();
        if(!StringUtils.isEmpty(parentId)) {
            Node parent = repo.getById(parentId);
            newNode.setParentId(parent.getId());
        }
        newNode = repo.persist(newNode);
        return newNode;
    }

    @DELETE
    @Secured
    public Node removeNode(@FormParam("nodeId") String nodeId) {
        Node node = repo.getById(nodeId);
        repo.remove(node);
        return node;
    }

}
