package com.dimazombie.famtree.model;

public interface UserRepository {
    public User findByLoginPassword(String login, String password);
}
