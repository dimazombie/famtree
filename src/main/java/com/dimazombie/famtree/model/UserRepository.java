package com.dimazombie.famtree.model;

public interface UserRepository {
    public User findByLogin(String login);
    public User findByLoginPassword(String login, String password);
}
