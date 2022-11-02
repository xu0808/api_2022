package com.pingan.ai.config;

import com.pingan.ai.model.common.MyException;
import com.pingan.ai.model.common.Result;
import com.pingan.ai.model.common.ResultTypeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @desc 异常处理类
 */
@ControllerAdvice
public class ExceptionHandle extends ResponseEntityExceptionHandler {

    /**
     * 只捕获 IllegalArgumentException 异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private Result illegalArgumentExceptionHandler(HttpServletRequest request, Exception e) {
        System.out.println("IllegalArgumentException 异常: " + e.getClass().getName());
        return Result.error(ResultTypeEnum.PARAM_ERROR,"请求地址："+request.getRequestURI());
    }

    /**
     * 只捕获 MyException 异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private Result myExceptionHandler(HttpServletRequest request,MyException e) {
        System.out.println("自定义异常 ："+ e.getMessage());
        return  Result.error(e.getMessage()+"url:"+request.getRequestURI());
    }

    /**
     * 默认异常捕获，上面两个异常没走的话走我。
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private Result exceptionHandler(HttpServletRequest request,Exception e) {
        System.out.println("异常 ："+ e.getClass().getName());
        request.getMethod();
        return Result.error(ResultTypeEnum.SERVICE_ERROR,"不符合上述异常的默认走我这");
    }
}