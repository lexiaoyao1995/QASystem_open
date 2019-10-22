package com.lexiaoyao.controller;

import com.lexiaoyao.service.UserService;
import com.lexiaoyao.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class ApiController {

    @Autowired
    private UserService userService;


    @GetMapping("user")
    public List<User> test2() {
        return userService.getAll();
    }

}
