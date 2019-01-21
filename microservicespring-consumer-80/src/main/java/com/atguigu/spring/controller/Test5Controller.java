package com.atguigu.spring.controller;

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

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

@Controller
public class Test5Controller {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/test5")
    @ResponseBody
    public String test5(){
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
            Integer jvalue = (Integer)ob.get("id");
            String url2="http://openapi.meituan.com/poi/area?cityid="+jvalue;
            try{
                ResponseEntity<String> results2 = restTemplate.exchange(url2, HttpMethod.GET, null, String.class);

            }catch (Exception e){
                hashMap.put("errorid",ob.get("id"));
                exportData.add(hashMap);
            }

        }
        //构建表头，即列名
        LinkedHashMap<String,String> headMap = new LinkedHashMap<>();
        Iterator<?> it3 = exportData.get(0).entrySet().iterator();
        while (it3.hasNext()){
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it3.next();

            String key =  entry.getKey();

                headMap.put(key,key);
        }
        CSVUtils.createCSVFile(exportData, headMap, "C:\\Users\\qingqingli\\temp\\csv", "cityxz");

        return jsonData;
    }
}
