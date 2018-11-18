package com.dimazombie.famtree.rest;

import com.dimazombie.famtree.model.Node;
import com.dimazombie.famtree.model.Person;
import com.dimazombie.famtree.web.Secured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Path("node")
@Produces("application/json")
public class NodeResource {
    private Logger logger = LoggerFactory.getLogger(NodeResource.class);

    @GET
    @Path("111")
    public List<Node> getAllNodes() {
        logger.debug("getAllNodes");
        Node ancestor1 = new Node(1L, new Person(11L,"John Doe",new Date()), null);
        Node ancestor2 = new Node(2L, new Person(12L,"Mary Jane",new Date()), null);
        Node member =  new Node(3L, new Person(13L,"Bill Billoff",new Date()),
                Arrays.asList(ancestor1, ancestor2));

        return Arrays.asList(member);
    }

    @GET
    @Secured
    @Path("222")
    public List<Node> getAllNodesSecured() {
        logger.debug("getAllNodesSecured");

        Node ancestor1 = new Node(1L, new Person(11L,"John Doe",new Date()), null);
        Node ancestor2 = new Node(2L, new Person(12L,"Mary Jane",new Date()), null);
        Node member =  new Node(3L, new Person(13L,"Bill Billoff",new Date()),
                Arrays.asList(ancestor1, ancestor2));

        return Arrays.asList(member);
    }
}
