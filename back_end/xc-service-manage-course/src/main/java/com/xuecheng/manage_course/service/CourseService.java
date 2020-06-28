package com.xuecheng.manage_course.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_course.dao.CourseMapper;
import com.xuecheng.manage_course.dao.TeachplanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private TeachplanMapper teachplanMapper;
    @Autowired
    private CourseMapper courseMapper;
    public TeachplanNode findTeachplanList(String courseId){
        TeachplanNode teachplanNode = teachplanMapper.selectList(courseId);
        return teachplanNode;
    }
    public QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest){
        PageHelper.startPage(page,size);
        Page<CourseInfo> courseInfos = courseMapper.findCourseInfoListWithPage();
        QueryResult queryResult=new QueryResult();
        queryResult.setTotal(courseInfos.getTotal());
        queryResult.setList(courseInfos.getResult());
        QueryResponseResult queryResponseResult=new QueryResponseResult(CommonCode.SUCCESS,queryResult);
        return queryResponseResult;
    }
}
