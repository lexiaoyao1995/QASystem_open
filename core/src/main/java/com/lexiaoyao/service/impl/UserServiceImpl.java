package com.lexiaoyao.service.impl;

import com.lexiaoyao.service.UserService;
import com.lexiaoyao.mapper.UserMapper;
import com.lexiaoyao.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAll() {
        return userMapper.getAll();
    }

    @Override
    public User getByName(String name) {
        return userMapper.findByUserName(name);
    }


    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }
}
