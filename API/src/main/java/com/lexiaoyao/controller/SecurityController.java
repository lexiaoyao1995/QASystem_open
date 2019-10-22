package com.lexiaoyao.controller;

import com.lexiaoyao.model.BusinessException;
import com.lexiaoyao.model.ErrorType;
import com.lexiaoyao.model.User;
import com.lexiaoyao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity authenticationRequire() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("请登录");
    }

    @PostMapping("/register")
    public ResponseEntity register(String username, String password) {

        if (userService.getByName(username) != null) {
            throw new BusinessException(ErrorType.USER_ALREADY_EXIST);
        }
        User user = new User();
        user.setUsername(username);
        String encode = passwordEncoder.encode(password);
        user.setPassword(encode);
        userService.insert(user);
        return ResponseEntity.ok(user);
    }

}
