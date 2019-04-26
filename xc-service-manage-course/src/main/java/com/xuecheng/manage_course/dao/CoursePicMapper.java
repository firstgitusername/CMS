package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CoursePic;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CoursePicMapper {
    CoursePic findCoursePicBycId(String courseId);
    Long deleteCoursePicBycid(String courseId);
}
