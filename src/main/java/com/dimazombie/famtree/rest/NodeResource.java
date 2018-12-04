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
    public Node addNewNode(@FormParam("nodeId") String parendNodeId) {
        Node newNode = null;
        if(StringUtils.isEmpty(parendNodeId)) {
            newNode = new Node(null, "Mee", null);
            repo.addNewNodes(newNode);
        } else {
            Node parent = repo.getNodeById(parendNodeId);
            if(parent.ancestors == null) {
                parent.ancestors = new ArrayList<Node>();
            }
            newNode = new Node(parent.getId(), "Parent", null);
            repo.addNewNodes(newNode);
            parent.ancestors.add(newNode);
        }
        return newNode;
    }

    @HEAD
    @Secured
    public Node addRootNode() {
        Node root = new Node(null, "Mee", null);
        repo.addNewNodes(root);
        return root;
    }

    @DELETE
    @Secured
    public Node removeNode(@FormParam("nodeId") String nodeId) {
        Node node = repo.getNodeById(nodeId);
        repo.removeNode(node);
        return node;
    }

}
