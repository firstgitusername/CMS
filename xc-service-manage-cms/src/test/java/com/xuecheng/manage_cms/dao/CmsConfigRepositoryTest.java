package com.xuecheng.manage_cms.dao;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-09-12 18:11
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsConfigRepositoryTest {

    @Autowired
    CmsConfigRepository cmsConfigRepository;
    @Test
    public void testFindOne(){
        Optional<CmsConfig> op = cmsConfigRepository.findById("5a791725dd573c3574ee333f");
        if(op.isPresent()){
            CmsConfig cmsConfig = op.get();
            System.out.print(cmsConfig);
        }

    }



}
