package com.hiveel.upload.web.interceptor;

import com.google.gson.Gson;
import com.hiveel.auth.sdk.model.Account;
import com.hiveel.core.debug.DebugSetting;
import com.hiveel.core.model.rest.BasicRestCode;
import com.hiveel.core.model.rest.Rest;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MgInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Account account = (Account) request.getAttribute("loginAccount");
        if (!account.getExtra().equals("MG")) {
            response.setContentType("application/json;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
            response.getWriter().print(new Gson().toJson(Rest.createFail(BasicRestCode.UNAUTHORIZED)));
            return false;
        }
        return true;
    }

}
