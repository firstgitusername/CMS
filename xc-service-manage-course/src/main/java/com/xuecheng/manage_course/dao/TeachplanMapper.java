package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Mapper
public interface TeachplanMapper{
    //根据课程id查询课程的学习计划
    public TeachplanNode selectList(String courseid);

    //查询课程计划根节点
    public List<TeachplanNode> selectNodes(@Param("courseid") String courseid, @Param("parentid")String parentid);
}
