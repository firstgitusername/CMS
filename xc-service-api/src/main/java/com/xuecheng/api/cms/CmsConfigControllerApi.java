package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="cms模板数据接口",description = "cms模板数据管理接口，提供模板数据的增、删、改、查")
public interface CmsConfigControllerApi {

    @ApiOperation("模板根据站点id查询")
    public CmsConfig findById(String id);
}
