package com.xuechen.manage_cms_client.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuechen.manage_cms_client.dao.CmsPageRepository;
import com.xuechen.manage_cms_client.dao.CmsSiteRepository;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Optional;

@Service
public class PageService {
    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    private static final Logger LOG= LoggerFactory.getLogger(PageService.class);

    public void  savePageToServerPath(String pageId){
        Optional<CmsPage> option = cmsPageRepository.findById(pageId);
        CmsPage cmsPage=null;
        if(option.isPresent()){
            cmsPage=option.get();
        }else{
            ExceptionCast.cast(CommonCode.SERVER_ERROR);
        }
        String htmlFileId = cmsPage.getHtmlFileId();
        InputStream in = this.getFileById(htmlFileId);
        if(in==null){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        String siteId = cmsPage.getSiteId();
        CmsSite cmsSite = this.getCmsSiteByid(siteId);
        if(StringUtils.isEmpty(cmsSite)){
            ExceptionCast.cast(CommonCode.SERVER_ERROR);
        }
        String pagePath=cmsSite.getSitePhysicalPath()+cmsPage.getPagePhysicalPath()+cmsPage.getPageName();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(pagePath);
            IOUtils.copy(in,fileOutputStream);
        } catch (Exception e) {
            LOG.error("SAVE PageHtml Id: "+pageId+"  To pagePath Error",e);
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_SAVEHTMLERROR);
        }


    }

    public InputStream getFileById(String id){
        InputStream in=null;
        try {
            GridFSFile gf = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(id)));
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gf.getObjectId());
            GridFsResource gridFsResource = new GridFsResource(gf, gridFSDownloadStream);
            in=gridFsResource.getInputStream();
        }catch (Exception e){
            LOG.error("GridFS Get File Error By id :"+id,e);
        }
        return in;
    }

    public CmsSite getCmsSiteByid(String id){
        Optional<CmsSite> op1 = cmsSiteRepository.findById(id);
        CmsSite cmsSite=null;
        if(op1.isPresent()){
            cmsSite=op1.get();
        }
        return cmsSite;
    }


}
