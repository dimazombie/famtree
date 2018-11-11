package com.dimazombie.famtree.rest;

import com.dimazombie.famtree.model.Node;
import com.dimazombie.famtree.model.Person;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Path("node")
@Produces("application/json")
public class NodeResource {
    @GET
    public List<Node> getNodes() {
        return Arrays.asList(
                new Node(1L,null, new Person("John",null,"Doe",new Date())),
                new Node(2L,1L, new Person("Mark",null,"Doe",new Date())),
                new Node(3L,1L, new Person("Susan",null,"Doe",new Date()))
        );
    }
}
