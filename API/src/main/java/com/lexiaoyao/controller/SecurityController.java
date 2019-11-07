package com.lexiaoyao.controller;

import com.lexiaoyao.model.BusinessException;
import com.lexiaoyao.model.ErrorType;
import com.lexiaoyao.model.User;
import com.lexiaoyao.service.UserService;
import com.lexiaoyao.support.VerifyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

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

//        throw new BusinessException(ErrorType.USERNAME_NOT_EXIST);

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

    @GetMapping("vercode")
    public void vercode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        VerifyCode vc = new VerifyCode();
        BufferedImage image = vc.getImage();
        String text = vc.getText();
        HttpSession session = request.getSession();
        session.setAttribute("index_code", text);
        VerifyCode.output(image, response.getOutputStream());

    }

}
