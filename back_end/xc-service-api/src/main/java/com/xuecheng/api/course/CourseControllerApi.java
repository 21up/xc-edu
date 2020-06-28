package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(value = "课程管理接口",description = "课程管理接口")
public interface CourseControllerApi {
    @ApiOperation("课程查询计划")
    TeachplanNode findTeachplanList(String courseId);
    @ApiOperation("查询课程信息")
    QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest);
}
