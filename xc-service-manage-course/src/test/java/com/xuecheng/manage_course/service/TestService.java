package com.xuecheng.manage_course.service;

import com.netflix.discovery.converters.Auto;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.manage_course.client.CmsPageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestService {
    @Autowired
    private CourseService courseService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CmsPageClient cmsPageClient;

    @Test
    public void testRibbon(){
        String serviceId="xc-service-manage-course";
        ResponseEntity<CmsPage> forEntity = restTemplate.getForEntity("http://" + serviceId + "/cms/page/get/5a795ac7dd573c04508f3a56", CmsPage.class);
        CmsPage body = forEntity.getBody();
        System.out.print(body.getPageName());
    }

    @Test
    public void testFeign(){
        CmsPageResult cr = cmsPageClient.findOne("5a795ac7dd573c04508f3a56");
        System.out.print(cr);

    }
}
