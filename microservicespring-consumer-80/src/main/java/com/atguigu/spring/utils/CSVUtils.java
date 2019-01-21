package com.atguigu.spring.utils;
 
import org.apache.commons.beanutils.BeanUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
 
 
public class CSVUtils {
 
    /**
     * 生成为CVS文件
     * @param exportData 源数据List
     * @param map csv文件的列表头map
     * @param outPutPath 文件路径
     * @param fileName 文件名称
     */
    public static File createCSVFile(List exportData, LinkedHashMap map,String outPutPath, String fileName){
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
            //System.out.println("csvFileOutputStream：" + csvFileOutputStream);
            //写入头部文件
           for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator.hasNext();){
               Map.Entry propertyEntry = (Map.Entry) propertyIterator.next();
               csvFileOutputStream.write( propertyEntry.getValue() != null ?
                       (String) propertyEntry.getValue() : "" + "\"");
               if (propertyIterator.hasNext()) {
                   csvFileOutputStream.write(",");
               }
           }
            //回车换行符
            csvFileOutputStream.newLine();
            //写入文件内容
            for (Iterator iterator = exportData.iterator();iterator.hasNext();){
                Object row = iterator.next();
                for (Iterator propertyIterator = map.entrySet().iterator();propertyIterator.hasNext();){
                    Map.Entry propertyEntry = (Map.Entry) propertyIterator.next();
                    //getProperty（第一个是JavaBean对象，第二个是要操作的属性名）
                    csvFileOutputStream.write(BeanUtils.getProperty(row, (String) propertyEntry.getKey()));
                    if (propertyIterator.hasNext()){
                        csvFileOutputStream.write(",");
                    }
                }
                if (iterator.hasNext()){
                    csvFileOutputStream.newLine();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
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
