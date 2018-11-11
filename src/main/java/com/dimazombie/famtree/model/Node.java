package com.dimazombie.famtree.model;

public class Node {
    Long id;
    Long parentId;
    Person person;

    public Node(Long id, Long parentId, Person person) {
        this.id = id;
        this.parentId = parentId;
        this.person = person;
    }

    public Node(Long parentId, Person person) {
        this.parentId = parentId;
        this.person = person;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
