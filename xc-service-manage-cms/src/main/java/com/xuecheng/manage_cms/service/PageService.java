package com.xuecheng.manage_cms.service;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.config.RabbitMqConfig;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-09-12 18:32
 **/
@Service
public class PageService {

    @Autowired
    CmsPageRepository cmsPageRepository;

    @Autowired
    CmsTemplateRepository cmsTemplateRepository;

    @Autowired
    GridFsService gridFsService;

    @Autowired
    ConfigService configService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RabbitTemplate rabbitTemplate;

    private Logger LOG= LoggerFactory.getLogger(PageService.class);
    /**
     * 页面查询方法
     * @param page 页码，从1开始记数
     * @param size 每页记录数
     * @param queryPageRequest 查询条件
     * @return
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest){
        if(null == queryPageRequest){
            queryPageRequest=new QueryPageRequest();
        }
        //分页参数
        if(page <=0){
            page = 1;
        }
        page = page -1;
        if(size<=0){
            size = 10;
        }
        Pageable pageable = PageRequest.of(page,size);
        ExampleMatcher em=ExampleMatcher.matching();
        CmsPage cmsPage=new CmsPage();
        if(!StringUtils.isEmpty(queryPageRequest.getSiteId())){
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        if(!StringUtils.isEmpty(queryPageRequest.getTemplateId())){
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }
        if(!StringUtils.isEmpty(queryPageRequest.getPageAliase())){
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
            em=em.withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains());
        }
        Example example=Example.of(cmsPage,em);
        Page<CmsPage> all = cmsPageRepository.findAll(example,pageable);
        QueryResult queryResult = new QueryResult();
        queryResult.setList(all.getContent());//数据列表
        queryResult.setTotal(all.getTotalElements());//数据总记录数
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS,queryResult);
        return queryResponseResult;
    }

    public CmsPageResult add(CmsPage cmspage){

        if(null==cmspage){
            ExceptionCast.cast(CommonCode.SERVER_ERROR);
        }
        CmsPage old=cmsPageRepository.findBySiteIdAndPageNameAndPageWebPath(
                cmspage.getSiteId(),cmspage.getPageName(),cmspage.getPageWebPath());
        if(null!=old){
            //已存在
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        CmsPage cp = cmsPageRepository.save(cmspage);
        return new CmsPageResult(CommonCode.SUCCESS,cp);
    }

    public CmsPageResult update(String id,CmsPage cmspage){
        if(null==cmspage){
            ExceptionCast.cast(CommonCode.SERVER_ERROR);
        }
        CmsPage old=this.getById(id);
        if(null != old){
            old.setTemplateId(cmspage.getTemplateId());
            old.setSiteId(cmspage.getSiteId());
            old.setPageAliase(cmspage.getPageAliase());
            old.setPageName(cmspage.getPageName());
            old.setPageWebPath(cmspage.getPageWebPath());
            old.setPagePhysicalPath(cmspage.getPagePhysicalPath());
            old.setDataUrl(cmspage.getDataUrl());
            CmsPage save=cmsPageRepository.save(old);
            if(null !=save){
                return new CmsPageResult(CommonCode.SUCCESS,save);
            }
        }
        return new CmsPageResult(CommonCode.FAIL,cmspage);


    }

    public CmsPage getById(String id){
        Optional<CmsPage> old = cmsPageRepository.findById(id);
        if(old.isPresent()) {
            return old.get();
        }
        return null;
    }

    public ResponseResult delete(String id){
        CmsPage cmspage=this.getById(id);
        if(null!=cmspage){
            cmsPageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    public String  getPageHtml(String pageId){
        //获取模板
        String template = this.getTemplateByPageId(pageId);
        if(StringUtils.isEmpty(template)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        Map model = this.getModelByPageId(pageId);
        if(StringUtils.isEmpty(model)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        String s = this.generateHtml(template, model);
        if(StringUtils.isEmpty(s)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        return s;

    }

    public String  generateHtml(String template,Map model){
        try {
            //生成配置类
            Configuration configuration = new Configuration(Configuration.getVersion());
            //模板加载器
            StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
            stringTemplateLoader.putTemplate("template",template);
            //配置模板加载器
            configuration.setTemplateLoader(stringTemplateLoader);
            //获取模板
            Template template1 = configuration.getTemplate("template");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template1, model);
            return html;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public String  getTemplateByPageId(String pageId){
        CmsPage cmsPage = this.getById(pageId);
        if(StringUtils.isEmpty(cmsPage)){
            ExceptionCast.cast(CmsCode.CMS_COURSE_PERVIEWISNULL);
        }
        String templateId = cmsPage.getTemplateId();
        Optional<CmsTemplate> op = cmsTemplateRepository.findById(templateId);
        String template="";
        if(op.isPresent()){
            CmsTemplate cmsTemplate = op.get();
            String fileId=cmsTemplate.getTemplateFileId();
            try {
                template=gridFsService.query(fileId);
            } catch (Exception e) {
                LOG.error(this.getClass()+" GET TEMPLATE ERROR: "+fileId,e.getCause());
                ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
            }
        }else{
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        return template;
    }

    public Map getModelByPageId(String pageId){
        CmsPage cmsPage = this.getById(pageId);
        if(StringUtils.isEmpty(cmsPage)){
            ExceptionCast.cast(CmsCode.CMS_COURSE_PERVIEWISNULL);
        }
        String dataUrl = cmsPage.getDataUrl();
        Map result=null;
        if(StringUtils.isEmpty(dataUrl)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }else{
            ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
            result = forEntity.getBody();
        }
        return result;

    }

    public CmsPage postPage(String pageId){
        //删除已经存在的静态页面
        CmsPage cmsPage = this.getById(pageId);
        if(cmsPage==null){
            ExceptionCast.cast(CommonCode.SERVER_ERROR);
        }
        String htmlFileId=cmsPage.getHtmlFileId();
        if(!StringUtils.isEmpty(htmlFileId)){
            try {
                gridFsService.delete(htmlFileId);
            }catch(Exception e){
                LOG.error("DELETE Old PageHtml Id: "+htmlFileId+" Error");
            }
        }
        //页面静态化
       String pageHtml= this.getPageHtml(pageId);
       //保存到gridfs中
        InputStream inputStream=null;
        String fileId="";
        try {
            inputStream = IOUtils.toInputStream(pageHtml, "utf-8");
            fileId = gridFsService.save(inputStream, cmsPage.getPageName(), "");
        }catch(Exception e){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_SAVEHTMLERROR);
        }
        //修改静态文件id
        cmsPage.setHtmlFileId(fileId);
        CmsPage cp = cmsPageRepository.save(cmsPage);
        this.sendPostPage(pageId);
        return cp;
    }

    public void sendPostPage(String pageId){
        CmsPage cmsPage = this.getById(pageId);
        if(cmsPage==null){
            ExceptionCast.cast(CommonCode.SERVER_ERROR);
        }
        Map<String, Object> message = new HashMap<String,Object>();
        message.put("pageId",cmsPage.getPageId());
        rabbitTemplate.convertAndSend(RabbitMqConfig.EX_ROUTING_CMS_POSTPAGE,cmsPage.getSiteId(),message);
    }





}
