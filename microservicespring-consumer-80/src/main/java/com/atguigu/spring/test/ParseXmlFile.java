package com.atguigu.spring.test;

import com.atguigu.spring.utils.CSVUtils;
import com.atguigu.spring.utils.CSVUtilsMap;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.*;

public class ParseXmlFile {
    public static void main(String[] args) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read("C:/Users/qingqingli/Desktop/test/facilities_cn.xml");
        List<Element> hotelFacilitiesList = document.getRootElement().selectNodes("/HotelFacilities/HotelFacilitiesList/HotelFacility/Facilities/Location");
        Iterator iterator1 = hotelFacilitiesList.iterator();


        Map<String,Set<String>> map = new HashMap<>();

        while (iterator1.hasNext()) {
            Element hotelFacility = (Element) iterator1.next();
            Set<String> set1 = map.get(hotelFacility.attribute("Id").getValue());
            if(set1!=null){
                set1.add(hotelFacility.attribute("Name").getValue());
                if(hotelFacility.attribute("Id").getValue().equals("100000205")){
                    System.out.println(hotelFacility.attribute("Name").getValue());
                }

                map.put(hotelFacility.attribute("Id").getValue(),set1);
            }else{
                Set<String> set2 = new HashSet<>();
                set2.add(hotelFacility.attribute("Name").getValue());
                map.put(hotelFacility.attribute("Id").getValue(),set2);
            }


        }
        System.out.println(map.toString());

        CSVUtilsMap.createCSVFile(map,"C:\\Users\\qingqingli\\temp\\csv", "aaaa");

    }

}
