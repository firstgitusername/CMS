package com.xuecheng.manage_course.service;


import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.domain.course.response.DeleteCourseResult;
import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.*;
import org.apache.ibatis.annotations.Case;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private static final Logger LOG= LoggerFactory.getLogger(CourseService.class);
    @Autowired
    private TeachplanMapper teachplanMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseBaseRepository courseBaseRepository;

    @Autowired
    private TeachplanRepository teachplanRepository;

    @Autowired
    private CoursePicRepository coursePicRepository;

    @Autowired
    private CoursePicMapper coursePicMapper;

    public TeachplanNode findTeachplanList(String courseId){
        return teachplanMapper.selectList(courseId);
    }

    public String  getTeachplanRoot(String courseId){
        CourseBase courseBase = courseMapper.findCourseBaseById(courseId);
        if(courseBase==null){
            return "";
        }
        List<TeachplanNode> teachplanNodes = teachplanMapper.selectNodes(courseId, "0");
        if(teachplanNodes==null || teachplanNodes.isEmpty()){
            //该课程没有配置课程计划
            TeachplanNode teachplanNodeRoot = new TeachplanNode();
            teachplanNodeRoot.setCourseid(courseId);
            teachplanNodeRoot.setPname(courseBase.getName());
            teachplanNodeRoot.setParentid("0");
            teachplanNodeRoot.setGrade("1");//一级
            teachplanNodeRoot.setStatus("0");//未发布
            TeachplanNode teachplanNode = teachplanRepository.save(teachplanNodeRoot);
            return teachplanNode.getId();
        }
        TeachplanNode root = teachplanNodes.get(0);
        return root.getId();
    }
    @Transactional
    public ResponseResult addTeachplan(Teachplan teachplan){
        String parentid = teachplan.getParentid();
        String courseid = teachplan.getCourseid();
        if(StringUtils.isEmpty(parentid)){
            //父节点为根节点
            parentid = this.getTeachplanRoot(courseid);
        }
        teachplan.setParentid(parentid);
        Optional<Teachplan> op = teachplanRepository.findById(parentid);
        if(!op.isPresent()){
            ExceptionCast.cast(CommonCode.SERVER_ERROR);
        }
        Teachplan teachplanPar = op.get();
        String grade = teachplanPar.getGrade();
        if("1".equals(grade)){
            teachplan.setGrade("2");
        }else if("2".equals(grade)){
            teachplan.setGrade("3");
        }
        teachplan.setCourseid(teachplanPar.getCourseid());
        if(StringUtils.isEmpty(teachplan.getStatus())){
            teachplan.setStatus("0");//状态：默认为未发布
        }
        Teachplan teachplanN = teachplanRepository.save(teachplan);
        ResponseResult responseResult = new ResponseResult(CommonCode.SUCCESS);
        return responseResult;
    }
    //课程新增
    public AddCourseResult addCourse(CourseBase courseBase){
        try {
            CourseBase courseBaseN= courseBaseRepository.save(courseBase);
            AddCourseResult addCourseResult = new AddCourseResult(CommonCode.SUCCESS,courseBaseN.getId());
            return addCourseResult;
        }catch(Exception e){
            LOG.error("Add Course Error ",e);
            ExceptionCast.cast(CommonCode.FAIL);
        }
        return new AddCourseResult(CommonCode.FAIL,"");
    }
    //新增课程图片
    public ResponseResult addCoursePic(String courseId,String pic){
        Optional<CoursePic> op = coursePicRepository.findById(courseId);
        CoursePic coursePic=null;
        if(op.isPresent()){
            coursePic=op.get();
        }
        if(coursePic==null){
            coursePic=new CoursePic();
        }
        coursePic.setCourseid(courseId);
        coursePic.setPic(pic);
        CoursePic save = coursePicRepository.save(coursePic);
        return new ResponseResult(CommonCode.SUCCESS);
    }
    //查询课程图片
    public CoursePic getCoursePicByCid(String courseId){
        CoursePic coursePic = coursePicMapper.findCoursePicBycId(courseId);
        return coursePic;
    }

    public ResponseResult deleteCoursePic(String courseId){
        //coursePicRepository.deleteById(courseId)
        Long row = coursePicMapper.deleteCoursePicBycid(courseId);
        if(row>=1){
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);

    }

}
