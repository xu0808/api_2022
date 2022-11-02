package com.pingan.ai.controller;

import com.pingan.ai.model.param.BaseParam;
import com.pingan.ai.model.testplatform.BaseModel;
import com.pingan.ai.model.testplatform.Response;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 测试平台api
 * @Author 家旭
 * @dateTime 20200917 0608
 */
@Api(tags = "微信小程序授权登录接口")
@RestController
@RequestMapping("/testPlatform")
@Slf4j
public class TestPlatformController {

    @ResponseBody
    @PostMapping("/dataAnalysis")
    public Response dataAnalysis(@Validated @RequestBody BaseParam data) {
        return response(data);
    }

    @ResponseBody
    @PostMapping("/numberFeature")
    public Response numberFeature(@RequestBody BaseModel data) {
        return response(data);
    }

    @ResponseBody
    @PostMapping("/enumFeature")
    public Response enumFeature(@RequestBody BaseModel data) {
        return response(data);
    }
    @ResponseBody
    @PostMapping("/dateFeature")
    public Response dateFeature(@RequestBody BaseModel data) {
        return response(data);
    }

    public Response response(BaseModel data) {
        Response response = new Response();
        response.setCode(200);
        response.setMessage("success");
        return response;
    }

    public Response response(BaseParam data) {
        Response response = new Response();
        response.setCode(200);
        response.setMessage("success");
        return response;
    }


}