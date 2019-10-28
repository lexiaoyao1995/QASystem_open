package com.lexiaoyao.advice;

import com.lexiaoyao.model.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 只能拦截到达SpringMVC的异常
 */
@org.springframework.web.bind.annotation.ControllerAdvice
@Slf4j
public class ControllerAdvice {
    @ExceptionHandler(BusinessException.class)//只要抛出了这个异常就会进入到这个方法
    @ResponseBody
    public ResponseEntity handUser(BusinessException ex) {
        return ResponseEntity.status(HttpStatus.OK).body(ex.getType().getMessage());
    }

}
