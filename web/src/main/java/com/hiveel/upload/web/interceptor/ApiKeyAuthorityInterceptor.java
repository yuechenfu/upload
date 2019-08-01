package com.hiveel.upload.web.interceptor;

import com.google.gson.Gson;
import com.hiveel.core.debug.DebugSetting;
import com.hiveel.core.log.util.LogUtil;
import com.hiveel.core.model.rest.BasicRestCode;
import com.hiveel.core.model.rest.Rest;
import com.hiveel.core.util.WebClientUtil;
import com.hiveel.upload.web.cache.ApiKeyCache;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ApiKeyAuthorityInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!ApiKeyCache.isExist(request.getHeader("apiKey"))) {
            response.setContentType("application/json;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type");
            Rest<Object> rest = Rest.createFail(BasicRestCode.UNAUTHORIZED);
            response.getWriter().write(new Gson().toJson(rest));
            LogUtil.info("遇到无权请求, 来自: " + WebClientUtil.getIpAddress(request) + "。 api: " + request.getHeader("apiKey"));
            return false;
        }
        return true;
    }
}
