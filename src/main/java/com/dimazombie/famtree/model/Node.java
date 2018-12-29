package com.dimazombie.famtree.model;

import com.google.gson.Gson;

import java.util.List;

public class Node {
    public Long id;
    public Long parentId;
    public String name;
    public String bio;
    public Long imageId;
    public String dateOfBirth;
    public List<Node> ancestors;

    public Node() {
    }

    public Node(Long id, Long parentId, String name, String bio, Long imageId, String dateOfBirth) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.bio = bio;
        this.imageId = imageId;
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

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
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

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
