package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;

@Api(value = "课程管理接口",description = "课程管理接口")
public interface CourseControllerApi {
    @ApiOperation("课程查询计划")
    TeachplanNode findTeachplanList(String courseId);
    @ApiOperation("查询课程列表信息")
    QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest);
    @ApiOperation("添加课程计划")
    ResponseResult addTeachPlan( Teachplan teachplan);
    @ApiOperation("添加课程基本信息")
    AddCourseResult addCourseBase(CourseBase courseBase);
    @ApiOperation("根据课程id获取课程基本信息")
    CourseBase getCoursebaseById(String courseId);
}
