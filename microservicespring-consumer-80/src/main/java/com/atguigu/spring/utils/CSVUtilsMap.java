package com.atguigu.spring.utils;
 
import org.apache.commons.beanutils.BeanUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


public class CSVUtilsMap {
 
    /**
     * 生成为CVS文件

     * @param map csv文件的列表头map
     * @param outPutPath 文件路径
     * @param fileName 文件名称
     */
    public static File createCSVFile(Map<String,Set<String>> map, String outPutPath, String fileName){
        File csvFile = null;
        BufferedWriter csvFileOutputStream = null;
        File file = new File(outPutPath);
        if (!file.exists()){
            file.mkdir();
        }
        //定义文件名格式并创建
        try {
            csvFile = new File(outPutPath+"/"+fileName+".csv");
            //创建临时文件会有一串随机数字
            //csvFile = File.createTempFile(fileName,".csv",new File(outPutPath));
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter
                    (new FileOutputStream(csvFile),"UTF-8"),1024);


           for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator.hasNext();){
               Map.Entry propertyEntry = (Map.Entry) propertyIterator.next();
               csvFileOutputStream.write( propertyEntry.getKey()+","+propertyEntry.getValue().toString());

               if (propertyIterator.hasNext()){
                   csvFileOutputStream.newLine();
               }
           }
            //回车换行符


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                csvFileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }
}
