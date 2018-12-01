package com.dimazombie.famtree.rest;

import com.dimazombie.famtree.model.Node;
import com.dimazombie.famtree.model.NodeRepository;
import com.dimazombie.famtree.web.Secured;
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
    public Node addParentNodes(@FormParam("nodeId") String nodeId) {
        Node node = repo.getNodeById(nodeId);
        Node momsNode = new Node(node.getId(), "Mom", null);
        Node dadsNode = new Node(node.getId(), "Dad", null);
        node.ancestors = new ArrayList<Node>();
        node.ancestors.add(repo.addNewNodes(momsNode));
        node.ancestors.add(repo.addNewNodes(dadsNode));
        return node;
    }

    @DELETE
    @Secured
    public Node removeNode(@FormParam("nodeId") String nodeId) {
        Node node = repo.getNodeById(nodeId);
        repo.removeNode(node);
        return node;
    }

}
