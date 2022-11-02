package com.pingan.ai.controller;

import com.pingan.ai.model.common.MyException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionController {

    /**
     * 抛出运行时异常
     */
    @GetMapping("/throwRuntimeException")
    public void throwRuntimeException(){
        throw new RuntimeException();
    }

    /**
     * 抛出运行时异常
     */
    @GetMapping("/throwIllegalArgumentException")
    public void throwIllegalArgumentException(){
        throw new IllegalArgumentException();
    }

    /**
     * 抛出自定义异常
     */
    @GetMapping("/throwMyException")
    public void throwMyException(){
        throw new MyException("我是主动抛出来的");
    }
}
