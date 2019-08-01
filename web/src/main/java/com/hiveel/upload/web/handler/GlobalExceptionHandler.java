package com.hiveel.upload.web.handler;

import com.hiveel.core.debug.DebugSetting;
import com.hiveel.core.exception.ParameterException;
import com.hiveel.core.exception.UnauthorizationException;
import com.hiveel.core.exception.type.RestCodeException;
import com.hiveel.core.log.util.LogUtil;
import com.hiveel.core.model.rest.BasicRestCode;
import com.hiveel.core.model.rest.Rest;
import com.hiveel.core.model.rest.RestCode;
import com.sun.xml.bind.v2.TODO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Rest<String> allExceptionHandler(HttpServletRequest request, Exception exception) {
        RestCode code = exception instanceof RestCodeException ? ((RestCodeException) exception).getCode()
                : exception instanceof UnauthorizationException ? BasicRestCode.UNAUTHORIZED
                : exception instanceof ParameterException ? BasicRestCode.PARAMETER
                : BasicRestCode.FAIL;
        Rest<String> rest = Rest.createFail(code);
        String message = LogUtil.getMessageAndLog(exception);
        if (DebugSetting.debug) {
            rest.setMessage(message);
        }
        return rest;
    }

}
