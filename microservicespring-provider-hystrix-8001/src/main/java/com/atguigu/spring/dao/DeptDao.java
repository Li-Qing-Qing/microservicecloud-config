package com.atguigu.spring.dao;

import com.atguigu.spring.entity.Dept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeptDao {
    List<Dept> findAll();

    Dept findById(Long id);
}
