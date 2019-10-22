package com.lexiaoyao.service;

import com.lexiaoyao.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getByName(String name);

    void insert(User user);

}
