package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.SysDicthinaryControllerApi;
import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_course.service.SysDicthinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sys")
public class SysDicthinaryController implements SysDicthinaryControllerApi {
    @Autowired
    private SysDicthinaryService sysDicthinaryService;
    @Override
    @GetMapping("/dictionary/get/{type}")
    public SysDictionary selectByType(@PathVariable("type") String type) {
        SysDictionary sysDictionary = new SysDictionary();
        sysDictionary.setDType(type);
        List<SysDictionary> list = sysDicthinaryService.select(sysDictionary);
        if(list==null || list.isEmpty()){
            return sysDictionary;
        }
        return list.get(0);
    }
}
