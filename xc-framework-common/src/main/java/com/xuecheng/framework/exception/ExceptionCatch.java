package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionCatch {
    private static final Logger LOGGER= LoggerFactory.getLogger(ExceptionCatch.class);

    private static ImmutableMap<Class<? extends Throwable>,ResultCode> EXCEPTIONS;

    protected static ImmutableMap.Builder<Class<? extends Throwable>,ResultCode> builder =
            ImmutableMap.builder();

    static{
        builder.put(HttpMessageNotReadableException.class, CommonCode.SERVER_ERROR);
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException exception){
        LOGGER.error("catch exception : {}\r\nexception: ",exception.getMessage(),exception);
        ResultCode code = exception.getCode();
        return new ResponseResult(code);

    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception){
        LOGGER.error("catch exception : {}\r\nexception: ",exception.getMessage(),exception);
        if(EXCEPTIONS==null){
            EXCEPTIONS=builder.build();
        }
        ResultCode code = EXCEPTIONS.get(exception.getClass());
        if(code!=null){
            return new ResponseResult(code);
        }else{
            return new ResponseResult(CommonCode.SERVER_ERROR);
        }

    }
}
