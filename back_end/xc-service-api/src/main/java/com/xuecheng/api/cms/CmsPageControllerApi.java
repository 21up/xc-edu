package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "cms页面管理接口",description = "提供页面的增删查改")
public interface CmsPageControllerApi {
    @ApiOperation("分页查询页面列表")
    QueryResponseResult findList(int page,int size,QueryPageRequest queryPageRequest);
    @ApiOperation("添加页面")
    CmsPageResult add(CmsPage cmsPage);
    @ApiOperation("通过id查询页面")
    CmsPage findById(String id);
    @ApiOperation("修改页面")
    CmsPageResult edit(String id,CmsPage cmsPage);
    @ApiOperation("删除页面")
    ResponseResult delete(String id);
    @ApiOperation("发布页面")
    ResponseResult post(String pageId);
}
