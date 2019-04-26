package com.xuecheng.manage_cms;

import com.xuecheng.manage_cms.service.GridFsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsGridFsServiceTest {
    @Autowired
    private GridFsService gridFsService;

    @Test
    public void GridFsServiceTest() throws Exception{
        String query = gridFsService.query("5a7719d76abb5042987eec3b");
        System.out.print(query);

    }

}
