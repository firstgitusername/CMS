package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsSiteControllerApi;
import com.xuecheng.api.cms.CmsTemplateControllerApi;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_cms.service.SiteService;
import com.xuecheng.manage_cms.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cms/template")
public class CmsTemplateController implements CmsTemplateControllerApi {

    @Autowired
    private TemplateService templateService;

    @GetMapping
    @Override
    public QueryResponseResult findAll() {
        return templateService.findAll();
    }

    @Override
    @GetMapping("/getBysetId/{setId}")
    public QueryResponseResult findBySiteId(@PathVariable("setId") String siteId) {
        return templateService.findBySiteId(siteId);
    }
}
