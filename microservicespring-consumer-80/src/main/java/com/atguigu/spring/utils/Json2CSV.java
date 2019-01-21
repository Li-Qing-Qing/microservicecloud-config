package com.atguigu.spring.utils;
 
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
 
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
 
 
public class Json2CSV {
 
    public static void main(String[] args) {
        String jsonData = getJson("json2/aa.txt");

        JSONObject jsonObject1 = JSONObject.parseObject(jsonData);
        JSONArray jsonArray = jsonObject1.getJSONArray("data");
        //生成list用以写入CSV
        LinkedList<LinkedHashMap<String, Object>> exportData = new LinkedList<>();
        //将JSONArray导入list
        assert jsonArray != null;
        for (Iterator<?> iterator = jsonArray.iterator(); iterator.hasNext();){
            JSONObject jsonObject= (JSONObject) iterator.next();
            //采用LinkHashMap保证写入有序
            LinkedHashMap<String,Object> dataMap =json2Map(jsonObject);
            exportData.add(dataMap);
        }
 
        //构建表头，即列名
        LinkedHashMap<String,String> headMap = new LinkedHashMap<>();
        Iterator<?> it = exportData.get(0).entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            String key =  entry.getKey();
            headMap.put(key,key);
            //System.out.println(headMap);
        }
        CSVUtils.createCSVFile(exportData, headMap, "C:\\Users\\qingqingli\\temp\\csv", "jsonCSV2");
    }
 
    /**
     *JSON转换为map
     */
    private static LinkedHashMap<String,Object> json2Map(JSONObject jsonObject) {
        //转换为map对象
        LinkedHashMap<String, Object> dateMap= new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()){
            dateMap.put(entry.getKey(), entry.getValue());
        }
        return dateMap;
    }
 
    /**
     * 获取json字符串
     * @param jsonFilePath 文件路径
     */
    public static String getJson(String jsonFilePath){
        URL url = Json2CSV.class.getClassLoader().getResource(jsonFilePath);
        if (null!=url){
            File file = new File(url.getFile());
            try {
                return FileUtils.readFileToString(file,"UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
 
}
