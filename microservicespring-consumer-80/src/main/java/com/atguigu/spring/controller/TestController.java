package com.atguigu.spring.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.spring.utils.CSVUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Controller
public class TestController {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/test1")
    @ResponseBody
    public String test1(){
        String url="http://openapi.meituan.com/poi/city";
        //String json =restTemplate.getForObject(url,Object.class);
        ResponseEntity<String> results = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        String jsonData = results.getBody();
        JSONObject jsonObject1 = JSONObject.parseObject(jsonData);
        JSONArray jsonArray = jsonObject1.getJSONArray("data");
        Iterator<Object> it = jsonArray.iterator();
        //生成list用以写入CSV
        LinkedList<Map<String, Object>> exportData = new LinkedList<>();
        while (it.hasNext()) {
            Map<String,Object> ob = (Map<String,Object>) it.next();
            LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
            hashMap.put("cName",ob.get("name"));
            hashMap.put("cId",ob.get("id"));
            hashMap.put("name","null");
            hashMap.put("id","null");
            exportData.add(hashMap);
            Object jvalue = ob.get("id");

            String url2="http://openapi.meituan.com/poi/area?cityid="+jvalue;
            //String json =restTemplate.getForObject(url,Object.class);
            ResponseEntity<String> results2 = restTemplate.exchange(url2, HttpMethod.GET, null, String.class);
            String jsonData2 = results2.getBody();
            JSONObject jsonObject2 = JSONObject.parseObject(jsonData2);
            JSONArray jsonArray2 = jsonObject2.getJSONArray("data");
            Iterator<Object> it2 = jsonArray2.iterator();
            while (it2.hasNext()) {
                Map<String,Object> ob2 = (Map<String,Object>)it2.next();
                LinkedHashMap<String, Object> hashMap2 = new LinkedHashMap<>();
                hashMap2.put("cName",ob.get("name"));
                hashMap2.put("cId",ob.get("id"));
                hashMap2.put("name",ob2.get("name"));
                hashMap2.put("id",ob2.get("id"));
                exportData.add(hashMap2);

            }
        }
        //构建表头，即列名
        LinkedHashMap<String,String> headMap = new LinkedHashMap<>();
        Iterator<?> it3 = exportData.get(0).entrySet().iterator();
        while (it3.hasNext()){
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it3.next();

            String key =  entry.getKey();

                headMap.put(key,key);



            System.out.println(headMap.toString());
        }
        CSVUtils.createCSVFile(exportData, headMap, "C:\\Users\\qingqingli\\temp\\csv", "cityxz");

        return jsonData;
    }
}
