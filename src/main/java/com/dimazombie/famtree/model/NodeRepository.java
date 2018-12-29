package com.dimazombie.famtree.model;

import java.util.List;

public interface NodeRepository {
    public List<Node> getAllNodes();
    public Node getById(String nodeId);
    public Node persist(Node node);
    public void remove(Node node);
}
