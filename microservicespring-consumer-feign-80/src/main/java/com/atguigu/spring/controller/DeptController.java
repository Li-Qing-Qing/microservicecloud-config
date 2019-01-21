package com.atguigu.spring.controller;

import com.atguigu.spring.entity.Dept;
import com.atguigu.spring.service.DeptClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class DeptController {

    @Autowired
    private DeptClientService deptClientService;

    @RequestMapping("/consumer/list")
    public List<Dept> list(){
        return deptClientService.findAll();
    }

    @RequestMapping("/consumer/get/{id}")
    public Dept get(@PathVariable Long id){
        return deptClientService.findById(id);

    }

}
