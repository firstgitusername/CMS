package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.RibbonControllerApi;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.manage_course.service.RibbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ribbon/test")
public class RibbonController implements RibbonControllerApi {

    @Autowired
    private RibbonService ribbonService;


    @Override
    @GetMapping("/cmspage/{id}")
    public CmsPageResult testFindCmspage(@PathVariable("id") String id) {
        return ribbonService.query(id);
    }
}
