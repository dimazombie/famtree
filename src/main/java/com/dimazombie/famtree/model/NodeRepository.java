package com.dimazombie.famtree.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class NodeRepository {
    public List<Node> getAllNodes() {
        Node ancestor1 = new Node(1L, new Person(11L,"John Doe 2",new Date()), null);
        Node ancestor2 = new Node(2L, new Person(12L,"Mary Jane 2",new Date()), null);
        Node member =  new Node(3L, new Person(13L,"Bill Billoff 2",new Date()),
                Arrays.asList(ancestor1, ancestor2));

        return Arrays.asList(member);
    }

    public Node persist(Node node) {
        return node;
    };
}
