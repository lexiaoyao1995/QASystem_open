package com.lexiaoyao.handler;

import com.lexiaoyao.model.BusinessException;
import com.lexiaoyao.model.ErrorType;
import com.lexiaoyao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;

@Component
public class AppUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.lexiaoyao.model.User user = userService.getByName(username);
        if (Objects.isNull(user)) {
            throw new BusinessException(ErrorType.USERNAME_NOT_EXIST);
        }
        return new User(username, user.getPassword(), Collections.emptyList());

    }
}
