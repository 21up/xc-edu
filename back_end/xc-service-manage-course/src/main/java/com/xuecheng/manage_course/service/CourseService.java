package com.xuecheng.manage_course.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CourseBaseRepository;
import com.xuecheng.manage_course.dao.CourseMapper;
import com.xuecheng.manage_course.dao.TeachplanMapper;
import com.xuecheng.manage_course.dao.TeachplanRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private TeachplanMapper teachplanMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseBaseRepository courseBaseRepository;
    @Autowired
    private TeachplanRepository teachplanRepository;
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

    @Transactional
    public ResponseResult addTeachPlan(Teachplan teachplan) {
        if (teachplan==null|| StringUtils.isEmpty(teachplan.getCourseid())
                ||StringUtils.isEmpty(teachplan.getPname())){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        String courseid = teachplan.getCourseid();
        String parentid = teachplan.getParentid();
        if (StringUtils.isEmpty(parentid)){
            //如果父节点为空则获取根节点
           parentid=getTeachplanRoot(courseid);
        }
        //取出父节点信息
        Optional<Teachplan> optional = teachplanRepository.findById(parentid);
        if (!optional.isPresent()){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        Teachplan teachplanParent = optional.get();
        String parentGrade = teachplanParent.getGrade();
        //设置页面form没有的信息
        teachplan.setParentid(parentid);
        teachplan.setStatus("0");
        if (parentGrade.equals("1")){
            teachplan.setGrade("2");
        }else if (parentGrade.equals("2")){
            teachplan.setGrade("3");
        }
        teachplan.setCourseid(teachplanParent.getCourseid());
        teachplanRepository.save(teachplan);
        return new ResponseResult(CommonCode.SUCCESS);
    }
    //获取课程根节点，如无则添加根节点
    private String getTeachplanRoot(String courseid) {
        Optional<CourseBase> optional = courseBaseRepository.findById(courseid);
        if (!optional.isPresent()){
            return null;
        }
        CourseBase courseBase = optional.get();
        List<Teachplan> teachplanList = teachplanRepository.findByCourseidAndParentid(courseid, "0");
        if (teachplanList==null||teachplanList.size()==0){
            Teachplan teachplan = new Teachplan();
            teachplan.setCourseid(courseid);
            teachplan.setPname(courseBase.getName());
            teachplan.setParentid("0");
            teachplan.setGrade("1");
            teachplan.setStatus("0");
            teachplanRepository.save(teachplan);
            return teachplan.getId();
        }
        Teachplan teachplan = teachplanList.get(0);
        return teachplan.getId();
    }
}
