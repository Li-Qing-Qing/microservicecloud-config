package com.atguigu.spring.controller;

import com.atguigu.spring.entity.Dept;
import com.atguigu.spring.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

    @RequestMapping("/findAll")
    public List<Dept>  findAll(){
        return deptService.list();
    }
    @RequestMapping("/find")
    public String  find(){
        return "Hello";
    }


}
