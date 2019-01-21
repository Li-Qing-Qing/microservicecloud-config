package com.atguigu.spring.service.impl;

import com.atguigu.spring.dao.DeptDao;
import com.atguigu.spring.entity.Dept;
import com.atguigu.spring.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptDao deptDao;
    @Override
    public List<Dept> list() {
        return deptDao.findAll();
    }
}
