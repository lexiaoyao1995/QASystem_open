package com.lexiaoyao.advice;

import com.lexiaoyao.model.BusinessException;
import com.lexiaoyao.model.ErrorType;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(BusinessException.class)//只要抛出了这个异常就会进入到这个方法
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handUser(BusinessException ex) {

        ErrorType type = ex.getType();
        Map<String, Object> map = new HashMap<>();
        if (ErrorType.USER_ALREADY_EXIST.equals(type)) {

            map.put("message", ErrorType.USER_ALREADY_EXIST.getMessage());
        } else {
            map.put("message", "未知错误");
        }
        return map;
    }

    @ExceptionHandler(JwtException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, Object> handUserExpireJWT(JwtException ex) {
        Map<String, Object> map = new HashMap<>();
        map.put("error", "身份过期");
        return map;
    }


}
