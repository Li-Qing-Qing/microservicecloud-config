package com.atguigu.spring.service;

import com.atguigu.spring.entity.Dept;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DeptClientServiceFallBackFactory implements FallbackFactory<DeptClientService> {
    @Override
    public DeptClientService create(Throwable throwable) {
        return new DeptClientService() {
            @Override
            public List<Dept> findAll() {
                List<Dept> deptList = new ArrayList<>();
                deptList.add(new Dept().setDname("该服务暂时没有响应"));
                return deptList;
            }

            @Override
            public Dept findById(Long id) {
                return new Dept().setDname("该服务没有响应");
            }
        };
    }
}
