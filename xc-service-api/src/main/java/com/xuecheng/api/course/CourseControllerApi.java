package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value="cms课程管理接口",description = "cms课程管理接口，提供课程的增、删、改、查")
public interface CourseControllerApi {

    @ApiOperation("课程计划查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="courseId",value = "课程id",required=true,paramType="path",dataType="String")
    })
    public TeachplanNode findTeachplanList(String courseId);

    @ApiOperation("课程计划新增")
    public ResponseResult addTeachplan(Teachplan teachplan);

    @ApiOperation("课程计划新增")
    public AddCourseResult addCourse(CourseBase courseBase);

    @ApiOperation("课程图片新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name="courseId",value = "课程id",required=true,paramType = "query",dataType="String"),
            @ApiImplicitParam(name="pic",value = "图片id",required=true,paramType = "query",dataType="String")

    })
    public ResponseResult addPicture(String courseId,String pic);

    @ApiOperation("课程图片查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="courseId",value = "课程id",required=true,paramType = "path",dataType="String")

    })
    public CoursePic queryCoursePic(String courseId);

    @ApiOperation("课程图片删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name="courseId",value = "课程id",required=true,paramType = "query",dataType="String")

    })
    public ResponseResult deleteCoursePic(String courseId);

}
