package com.dimazombie.famtree.rest;

import com.dimazombie.famtree.model.Node;
import com.dimazombie.famtree.model.NodeRepository;
import com.dimazombie.famtree.model.Person;
import com.dimazombie.famtree.web.Secured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Arrays;
import java.util.Date;
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
    public Node addNewNodes(@FormParam("nodeId") String nodeId) {
        //Node node = repo.getNodeById(nodeId);
        Node node = new Node(4L,new Person(14L,null, null), null);
        Node ancestor1 = new Node(5L, new Person(15L,"Mom", new Date().toString()), null);
        Node ancestor2 = new Node(6L, new Person(16L,"Dad", new Date().toString()), null);
        node.setAncestors(Arrays.asList(ancestor1, ancestor2));

        return repo.persist(node);
    }
}
