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
public class CmsTemplateRepositoryTest {
    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;

    @Test
    public void testFindAll(){
        List<CmsTemplate> all=cmsTemplateRepository.findAll();
        System.out.print(all);
    }

    @Test
    public void findBySiteId(){
        List<CmsTemplate> all=cmsTemplateRepository.findBySiteId("5a751fab6abb5044e0d19ea1");
        System.out.print(all);
    }
}
