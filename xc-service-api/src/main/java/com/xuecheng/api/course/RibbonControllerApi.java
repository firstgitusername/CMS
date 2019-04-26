package com.xuecheng.api.course;

import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value="ribbon远程调用",description = "ribbon远程调用")
public interface RibbonControllerApi {

    @ApiOperation("course调用page测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "页面id",required=true,paramType="path",dataType="String")
    })
    public CmsPageResult testFindCmspage(String id);
}
