package com.example.cinema.interceptor;

import com.example.cinema.config.InterceptorConfiguration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author huwen
 * @date 2019/3/23
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception{
        HttpSession session=httpServletRequest.getSession();
        if(null!=session && null!=session.getAttribute(InterceptorConfiguration.SESSION_KEY)){
            return true;
        }
        httpServletResponse.sendRedirect("/index");
        return false;
    }
}
