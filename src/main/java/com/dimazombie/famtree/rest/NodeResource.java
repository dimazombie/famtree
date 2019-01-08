package com.dimazombie.famtree.rest;

import com.dimazombie.famtree.model.Node;
import com.dimazombie.famtree.model.NodeRepository;
import com.dimazombie.famtree.web.Secured;
import org.apache.cxf.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
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
            Node parent = repo.findById(parentId);
            newNode.setParentId(parent.getId());
        }
        newNode = repo.persist(newNode);
        return newNode;
    }

    @DELETE
    @Secured
    public Node removeNode(@FormParam("nodeId") String nodeId) {
        Node node = repo.findById(nodeId);
        repo.remove(node);
        return node;
    }

    @PUT
    @Secured
    public Node saveNode(@FormParam("id") String nodeId, @FormParam("parentId") String parentId,
                         @FormParam("name") String name, @FormParam("bio") String bio,
                         @FormParam("imageId") String imageId, @FormParam("dateOfBirth") String dateOfBirth) {
        Node node = repo.findById(nodeId);
        if(!StringUtils.isEmpty(parentId)) {
            node.setParentId(Long.valueOf(parentId));
        }
        node.setName(name);
        node.setBio(bio);
        if(!StringUtils.isEmpty(imageId)) {
            node.setImageId(Long.valueOf(imageId));
        }
        node.setDateOfBirth(dateOfBirth);
        repo.persist(node);
        return node;
    }

}
