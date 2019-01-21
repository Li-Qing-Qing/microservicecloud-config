package com.atguigu.spring.test;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ParseXmlFile2 {
    public static void main(String[] args) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read("C:/Users/qingqingli/Desktop/test/facilities_cn.xml");
        List<Element> hotelFacilitiesList = document.getRootElement().selectNodes("/HotelFacilities/HotelFacilitiesList/HotelFacility");
        Iterator iterator1 = hotelFacilitiesList.iterator();



        while (iterator1.hasNext()) {
            Element hotelFacility = (Element) iterator1.next();
            List<Element> locationList = hotelFacility.selectNodes("/HotelFacility/Facilities/Location");
            Iterator iterator2 = locationList.iterator();
            while (iterator2.hasNext()) {
                Element location = (Element) iterator2.next();
                System.out.println(location.asXML());
                System.out.println(location.attribute("Id").getValue());
            }

        }
    }
}
