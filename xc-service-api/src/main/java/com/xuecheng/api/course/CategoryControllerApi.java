package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="cms课程类型管理接口",description = "cms课程类型管理接口，提供课程类型的增、删、改、查")
public interface CategoryControllerApi {
    @ApiOperation("课程类型查询")
    public CategoryNode findCategoryList();
}
