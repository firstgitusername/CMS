package com.xuecheng.manage_cms.dao;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsGridFsTest {
    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

    @Test
    public void save() throws Exception{
        File file = new File("D:/learn/cms/ftl/index_banner.ftl");
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectId id = gridFsTemplate.store(fileInputStream, "", "");
        System.out.print(id.toString());
    }
    @Test
    public void query() throws Exception{
        String fileId="5c57bd7263e3e239685b7c0b";
        GridFSFile gf = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gf.getObjectId());
        GridFsResource gridFsResource = new GridFsResource(gf, gridFSDownloadStream);
        String s = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
        System.out.print(s);
    }

    @Test
    public void delete() throws Exception{
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is("5c541fdb63e3e207586bb198")));
        query();

    }




}
