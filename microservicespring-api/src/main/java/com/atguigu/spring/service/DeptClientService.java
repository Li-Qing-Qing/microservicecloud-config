package com.atguigu.spring.service;

import com.atguigu.spring.entity.Dept;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "MICROSERVICECLOUD-DEPT",fallbackFactory = DeptClientServiceFallBackFactory.class)
public interface DeptClientService {

    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public List<Dept> findAll();

    @RequestMapping(value = "/get/{id}")
    public Dept findById(@PathVariable("id") Long id);
}
