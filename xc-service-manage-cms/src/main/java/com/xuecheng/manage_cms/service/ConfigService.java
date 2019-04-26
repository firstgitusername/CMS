package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfigService {
    @Autowired
    private CmsConfigRepository cmsConfigRepository;

    public CmsConfig findOne(String id){
        Optional<CmsConfig> op = cmsConfigRepository.findById(id);
        if(op.isPresent()){
            return op.get();
        }
        return null;
    }



}
