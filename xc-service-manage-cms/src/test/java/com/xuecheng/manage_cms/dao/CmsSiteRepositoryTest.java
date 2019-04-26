package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsSiteRepositoryTest {
    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;

    @Test
    public void testFindAll(){
        List<CmsSite> all=cmsSiteRepository.findAll();
        System.out.print(all);
    }

    @Test
    public void testFindSiteAndTemplate(){
        List<CmsSite> all=cmsSiteRepository.findAll();
        for (CmsSite cmsSite:all){
            List<CmsTemplate> cmsTemplates= cmsTemplateRepository.findBySiteId(cmsSite.getSiteId());
            cmsSite.setCmsTemplates(cmsTemplates);
        }
        System.out.print(all);
    }

}
