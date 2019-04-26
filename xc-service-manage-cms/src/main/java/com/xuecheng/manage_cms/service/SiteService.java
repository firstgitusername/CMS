package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteService {

    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;

    public QueryResponseResult findAll(){
        List<CmsSite> all=cmsSiteRepository.findAll();
        QueryResult queryResult=new QueryResult<CmsSite>();
        queryResult.setList(all);
        queryResult.setTotal(all.size());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }
    public QueryResponseResult findSiteAndTemplate(){
        List<CmsSite> all=cmsSiteRepository.findAll();
        for (CmsSite cmsSite:all){
            List<CmsTemplate> cmsTemplates= cmsTemplateRepository.findBySiteId(cmsSite.getSiteId());
            cmsSite.setCmsTemplates(cmsTemplates);
        }
        QueryResult queryResult=new QueryResult<CmsSite>();
        queryResult.setList(all);
        queryResult.setTotal(all.size());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }
}
