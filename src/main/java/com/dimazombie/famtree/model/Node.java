package com.dimazombie.famtree.model;

import java.util.List;

public class Node {
    Long id;
    Person member;
    List<Node> ancestors;

    public Node(Long id, Person member, List<Node> ancestors) {
        this.id = id;
        this.member = member;
        this.ancestors = ancestors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getMember() {
        return member;
    }

    public void setMember(Person member) {
        this.member = member;
    }

    public List<Node> getAncestors() {
        return ancestors;
    }

    public void setAncestors(List<Node> ancestors) {
        this.ancestors = ancestors;
    }
}
