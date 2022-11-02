package com.pingan.ai.util;

import com.alibaba.fastjson.JSONObject;
import com.pingan.ai.busy.testplatform.DataAnalysisBusy;
import com.pingan.ai.busy.testplatform.DateFeatureBusy;
import com.pingan.ai.busy.testplatform.EnumFeatureBusy;
import com.pingan.ai.busy.testplatform.NumberFeatureBusy;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
public class HttpClient {

    public static JSONObject post(String url, JSONObject data) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity(data, headers);
        ResponseEntity<JSONObject> responseEntity = client.postForEntity(url, request, JSONObject.class);
        return responseEntity.getBody();
    }

    public static void main(String[] args) {
        String url = "http://localhost:8080/testPlatform/dataAnalysis";
        JSONObject response = post(url, JSONObject.parseObject(DataAnalysisBusy.json));
        System.out.println(String.format("url = %s, response  = %s", url, response));

//        url = "http://localhost:8080/testPlatform/numberFeature";
//        response = post(url, JSONObject.parseObject(NumberFeatureBusy.json));
//        System.out.println(String.format("url = %s, response  = %s", url, response));
//
//        url = "http://localhost:8080/testPlatform/enumFeature";
//        response = post(url, JSONObject.parseObject(EnumFeatureBusy.json));
//        System.out.println(String.format("url = %s, response  = %s", url, response));
//
//        url = "http://localhost:8080/testPlatform/dateFeature";
//        response = post(url, JSONObject.parseObject(DateFeatureBusy.json));
//        System.out.println(String.format("url = %s, response  = %s", url, response));
    }
}
