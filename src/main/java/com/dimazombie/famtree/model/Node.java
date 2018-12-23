package com.dimazombie.famtree.model;

import javax.persistence.Access;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.GenerationType.AUTO;

@Entity @Access(FIELD)
public class Node implements Serializable {
    @GeneratedValue(strategy = AUTO)
    @Id public Long id;

    public Long parentId;
    public String name;
    public String bio;
    public String dateOfBirth;
    public List<Node> ancestors;

    public Node() {
    }

    public Node(Long id, Long parentId, String name, String bio, String dateOfBirth) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.bio = bio;
        this.dateOfBirth = dateOfBirth;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Node> getAncestors() {
        return ancestors;
    }

    public void setAncestors(List<Node> ancestors) {
        this.ancestors = ancestors;
    }
}
