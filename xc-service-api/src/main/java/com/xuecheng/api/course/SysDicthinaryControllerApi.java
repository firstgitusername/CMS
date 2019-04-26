package com.xuecheng.api.course;

import com.xuecheng.framework.domain.system.SysDictionary;
import io.swagger.annotations.*;

@Api(value="数据字典维护接口",description = "数据字典维护接口，提供数据字典的增、删、改、查")
public interface SysDicthinaryControllerApi {
    @ApiOperation("根据类型查询数据字典")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type",value = "类型",required=true,paramType="path",dataType="String"),
    })
    public SysDictionary selectByType(String type);
}
