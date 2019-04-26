package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_course.dao.SysDicthinaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDicthinaryService {
    @Autowired
    private SysDicthinaryRepository sysDicthinaryRepository;

    //条件查询
    public List<SysDictionary> select(SysDictionary sysDictionary){
        if(sysDictionary==null){
            sysDictionary=new SysDictionary();
        }
        //ExampleMatcher em=ExampleMatcher.matching();
        //em = em.withMatcher("_class", ExampleMatcher.GenericPropertyMatchers.ignoreCase());
        //em = em.withIgnoreCase("_class");
        Example example = Example.of(sysDictionary);
        List<SysDictionary> sysDictionaryList = sysDicthinaryRepository.findAll(example);
        return sysDictionaryList;
    }

}
