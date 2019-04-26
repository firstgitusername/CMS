package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-09-12 18:11
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    @Autowired
    CmsPageRepository cmsPageRepository;

    @Test
    public void testFindAll(){
        List<CmsPage> all = cmsPageRepository.findAll();
        System.out.println(all);

    }

    //分页查询
    @Test
    public void testFindPage(){
        //分页参数
        int page = 1;//从0开始
        int size = 10;
        Pageable pageable = PageRequest.of(page,size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        System.out.println(all);
    }

    //修改
    @Test
    public void testUpdate() {
        //查询对象
        Optional<CmsPage> optional = cmsPageRepository.findById("5b4b1d8bf73c6623b03f8cec");
        if(optional.isPresent()){
            CmsPage cmsPage = optional.get();
            //设置要修改值
            cmsPage.setPageAliase("test01");
            //...
            //修改
            CmsPage save = cmsPageRepository.save(cmsPage);
            System.out.println(save);
        }

    }

    //根据页面名称查询
    @Test
    public void testfindByPageName(){
        CmsPage cmsPage = cmsPageRepository.findByPageName("测试页面");
        System.out.println(cmsPage);
    }

    @Test
    public void testfindAllByExmaple(){
        //分页
        int page = 1;//从0开始
        int size = 10;
        Pageable pageable = PageRequest.of(page,size);
        //条件
        CmsPage cmsPage=new CmsPage();
        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        cmsPage.setTemplateId("5aec5dd70e661808240ab7a6");
        cmsPage.setPageAliase("预览");
        ExampleMatcher em=ExampleMatcher.matching().withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains());
        Example example=Example.of(cmsPage,em);
        List<CmsPage> all=cmsPageRepository.findAll(example);
        System.out.print(all);
    }

    //根据站点id、页面名称和页面路径查询
    @Test
    public void  testFindBySiteIdAndPageNameAndPageWebPath(){
        CmsPage cmsPage= cmsPageRepository.findBySiteIdAndPageNameAndPageWebPath(
                "5a751fab6abb5044e0d19ea1","index.html","/index.html");
        System.out.print(cmsPage);
    }

    //页面新增
    @Test
    public void testSave(){
        CmsPage cmsPage= new CmsPage();
        cmsPage.setPageAliase("测试");
        cmsPage.setTemplateId("5a962b52b00ffc514038faf7");
        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        cmsPage.setPageName("测试");
        CmsPage cmsP=cmsPageRepository.save(cmsPage);
        System.out.print(cmsP);
    }

    @Test
    public void testDelete(){
        cmsPageRepository.deleteById("112344");

    }

}
