package com.atguigu.spring.controller;

import com.atguigu.spring.entity.Dept;
import com.atguigu.spring.service.DeptService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    //一旦服务调用失败或者抛出异常的时候，就会自动调用@HystrixCommand中标注好的fallbackMethod中指定的方法。
    @HystrixCommand(fallbackMethod = "processHystrix_Get")
    public Dept  get(@PathVariable("id") Long id){
        Dept dept = deptService.findByID(id);
        if(dept==null){
            throw new RuntimeException("该"+id+"不存在");
        }
        return dept;
    }

    public Dept processHystrix_Get(@PathVariable("id") Long id){
        return new Dept().setDeptno(id).setDname("该"+id+"没有对应的信息").setDb_source("NO_dataSource");
    }
}
