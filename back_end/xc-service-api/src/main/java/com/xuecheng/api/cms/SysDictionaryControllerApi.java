package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.system.SysDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "数据字典接口")
public interface SysDictionaryControllerApi {
    @ApiOperation("数据字典查询")
    SysDictionary getByType(String type);
}
