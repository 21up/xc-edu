package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.omg.PortableServer.THREAD_POLICY_ID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionCatch {
    private static final Logger LOGGER= LoggerFactory.getLogger(ExceptionCatch.class);
    //使用EXCEPTIONS存放异常类型和错误代码的映射，ImmutableMap的特点的一旦创建不可改变，并且线程安全
    private static ImmutableMap<Class<? extends Throwable>,ResultCode> EXCEPTIONS;
    //使用builder来构建一个异常类型和错误代码的异常
    protected static ImmutableMap.Builder<Class<? extends Throwable>,ResultCode> builder=ImmutableMap.builder();
    //捕获CustomException异常
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult getCustomExceptin(CustomException e){
        LOGGER.error("catch exception :{}\r\nexception:",e.getMessage(),e);
        ResultCode resultCode=e.getResultCode();
        ResponseResult responseResult = new ResponseResult(resultCode);
        return responseResult;
    }
    //捕获Exception异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult getExceptin(Exception e){
        LOGGER.error("catch exception :{}\r\nexception:",e.getMessage(),e);
        if (EXCEPTIONS==null){
            EXCEPTIONS=builder.build();
        }
         ResultCode resultCode = EXCEPTIONS.get(e.getClass());
         ResponseResult responseResult;
        if (resultCode!=null){
            responseResult=new ResponseResult(resultCode);
        }else {
            responseResult=new ResponseResult(CommonCode.SERVER_ERROR);
        }
        return responseResult;
    }
    static {
        builder.put(HttpMessageNotReadableException.class,CommonCode.INVALID_PARAM);
    }
}