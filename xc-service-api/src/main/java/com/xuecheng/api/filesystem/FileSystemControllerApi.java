package com.xuecheng.api.filesystem;

import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.*;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "统一文件系统管理",description = "文件的上传、下载和查询功能")
public interface FileSystemControllerApi {
    @ApiOperation("文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name="filetag",value = "文件标签",required=true,paramType="query",dataType = "String"),
            @ApiImplicitParam(name="businesskey",value = "业务key",required=true,paramType="query",dataType = "String"),
            @ApiImplicitParam(name="metadata",value = "元数据",required=true,paramType="query",dataType = "String")
    })
    public UploadFileResult upload(@ApiParam(value="上传文件",required=true)MultipartFile multipartFile,
                                   String filetag,
                                   String businesskey,
                                   String metadata);
}
