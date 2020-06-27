package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api("课程管理接口")
public interface CourseControllerApi {
    @ApiOperation("课程查询计划")
    TeachplanNode findTeachplanList(String courseId);
}
