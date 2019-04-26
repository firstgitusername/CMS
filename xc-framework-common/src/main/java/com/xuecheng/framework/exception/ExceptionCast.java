package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

public class ExceptionCast {
    public static void cast(ResultCode code){
        throw new CustomException(code);
    }

}
