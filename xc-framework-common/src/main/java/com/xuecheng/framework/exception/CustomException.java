package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;
import net.bytebuddy.implementation.bind.annotation.Super;

public class CustomException extends RuntimeException {
    private ResultCode code;

    public CustomException(ResultCode code){
        super("错误代码："+code.code()+"错误信息："+code.message());
        this.code=code;
    }

    public ResultCode getCode() {
        return code;
    }

    public void setCode(ResultCode code) {
        this.code = code;
    }
}
