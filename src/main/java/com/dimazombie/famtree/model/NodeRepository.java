package com.dimazombie.famtree.model;

import java.util.List;

public interface NodeRepository {
    public List<Node> getAllNodes();
    public Node getNodeById(String nodeId);
    public Node addNewNodes(Node node);
    public void removeNode(Node node);
}
