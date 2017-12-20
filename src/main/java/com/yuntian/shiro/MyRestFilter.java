package com.yuntian.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter;
@Slf4j
public class MyRestFilter extends HttpMethodPermissionFilter{
	
	@Override
	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		//request.getAttribute("accessToken");
		String token = request.getParameter("accessToken");
		HttpServletRequest req = (HttpServletRequest)request;
		log.debug("MyRestFilter.isAccessAllowed...." + req.getServletPath());
		return true;
    }
	
	@Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException{
		onLoginFail(response); //6、登录失败
        return false;
    }
	
	//登录失败时默认返回401状态码
    private void onLoginFail(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("login error");
    }
}
