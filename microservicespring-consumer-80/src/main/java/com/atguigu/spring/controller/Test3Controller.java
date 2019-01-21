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
public class Test3Controller {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/test3")
    @ResponseBody
    public String test3(){
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
            //String json =restTemplate.getForObject(url,Object.class);

            try{
                ResponseEntity<String> results2 = restTemplate.exchange(url2, HttpMethod.GET, null, String.class);
                String jsonData2 = results2.getBody();
                JSONObject jsonObject2 = JSONObject.parseObject(jsonData2);
                JSONArray jsonArray2 = jsonObject2.getJSONArray("data");
                Iterator<Object> it2 = jsonArray2.iterator();
                while (it2.hasNext()) {
                    Map<String,Object> ob2 = (Map<String,Object>)it2.next();

                    if(((JSONArray)ob2.get("area")).size()>0){
                        /*JSONObject jsonObject3 = JSONObject.parseObject((String) ob2.get("area"));*/
                        JSONArray jsonArray3 = (JSONArray) ob2.get("area");
                        Iterator<Object> it3 = jsonArray3.iterator();
                        while (it3.hasNext()) {
                            LinkedHashMap<String, Object> hashMap2 = new LinkedHashMap<>();
                            Map<String, Object> ob3 = (Map<String, Object>) it3.next();
                            hashMap2.put("城市id", ob.get("id"));
                            hashMap2.put("城市名称", ob.get("name"));
                            hashMap2.put("行政区id", ob2.get("id"));
                            hashMap2.put("行政区名称", ob2.get("name"));
                            hashMap2.put("商圈id", ob3.get("id"));
                            hashMap2.put("商圈名称", ob3.get("name"));
                            exportData.add(hashMap2);
                        }
                    }else{
                        LinkedHashMap<String, Object> hashMap2 = new LinkedHashMap<>();
                        hashMap2.put("城市id", ob.get("id"));
                        hashMap2.put("城市名称", ob.get("name"));
                        hashMap2.put("行政区id", ob2.get("id"));
                        hashMap2.put("行政区名称", ob2.get("name"));
                        hashMap2.put("商圈id", "null");
                        hashMap2.put("商圈名称", "null");

                        exportData.add(hashMap2);
                    }

                }

            }catch (Exception e){

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
