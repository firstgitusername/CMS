package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;

@Api(value="cms页面管理接口",description = "cms页面管理接口，提供页面的增、删、改、查")
public interface CmsPageControllerApi {
    //页面查询
    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "页码",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="size",value = "每页记录数",required=true,paramType="path",dataType="int")
    })
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);


    @ApiOperation("根据id查询页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "页面id",required=true,paramType="path",dataType="String")
    })
    public CmsPageResult findOne(String id);

    @ApiOperation("页面新增")
    public CmsPageResult addOne(CmsPage cmsPage);

    @ApiOperation("页面修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "页面id",required=true,paramType="path",dataType="String")
    })
    public CmsPageResult updateOne(String id,CmsPage cmsPage);

    @ApiOperation("页面删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "页面id",required=true,paramType="path",dataType="String")
    })
    public ResponseResult deleteOne(String id);

    @ApiOperation("页面发布")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageId",value = "页面id",required=true,paramType="path",dataType="String")
    })
    public ResponseResult post(String pageId);
}
