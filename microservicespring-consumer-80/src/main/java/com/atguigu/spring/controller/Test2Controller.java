package com.atguigu.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class Test2Controller {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/test2")
    @ResponseBody
    public String test2() {
        String url = "http://openapi.meituan.com/poi/area?cityid=1";
        //String json =restTemplate.getForObject(url,Object.class);
        ResponseEntity<String> results = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        String jsonData = results.getBody();
        return jsonData;
    }

}
