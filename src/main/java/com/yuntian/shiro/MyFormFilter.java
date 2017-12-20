package com.yuntian.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuntian.domain.SysUser;

public class MyFormFilter extends FormAuthenticationFilter{
	
	private static Logger log = LoggerFactory.getLogger(HttpMethodPermissionFilter.class);
	
//	@Override
//	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//		boolean ret = super.isAccessAllowed(request, response, mappedValue);
//		//request.getAttribute("accessToken");
//		super.isAccessAllowed(request, response, mappedValue);
//		String token = request.getParameter("accessToken");
//		HttpServletRequest req = (HttpServletRequest)request;
//		System.out.println("MyRestFilter.isAccessAllowed...." + req.getServletPath());
//		return ret;
//    }
	
//	@Override
//    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception{
//		//super.onAccessDenied(request, response);
//		onLoginFailure(response); //6、登录失败
//        return false;
//    }
	

	//登录失败时默认返回401状态码
    private void onLoginFailure(ServletRequest request,ServletResponse response) throws IOException {
    	
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
		String exception = (String) request.getAttribute("shiroLoginFailure");
		System.out.println("exception=" + exception);
		String msg = null;
		if (exception != null) {
			if (UnknownAccountException.class.getName().equals(exception)) {
				System.out.println("账号不存在：");
				msg = "账号不存在：";
			} else if (IncorrectCredentialsException.class.getName().equals(exception)) {
				System.out.println("密码不正确：");
				msg = "密码不正确：";
			} else if ("kaptchaValidateFailed".equals(exception)) {
				System.out.println("验证码错误");
				msg = "验证码错误";
			} else if(AuthenticationException.class.getName().equals(exception)){
				System.out.println("密码不正确");
				msg = "密码不正确";
			}
		}

        
        httpResponse.getWriter().write(msg);
    }
	@Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
            ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // 获取用户信息
		SysUser user = (SysUser)subject.getPrincipal();
		user.setPassword(null);
		user.setRoleList(null);
		ObjectMapper mapper = new ObjectMapper();
		String userstr = mapper.writeValueAsString(user);
		// 返回前端
        //httpResponse.getWriter().write(userstr);
		//issueSuccessRedirect(request, response);
		//we handled the success redirect directly, prevent the chain from continuing:
		return true;
	}
		
	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e,
		            ServletRequest request, ServletResponse response) {
		if (log.isDebugEnabled()) {
		log.debug( "Authentication exception", e );
		}
		setFailureAttribute(request, e);
		try {
			onLoginFailure(request,response);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //6、登录失败
		return false;
	}
		   
	@Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                //allow them to see the login page ;)
                return true;
            }
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
                        "Authentication url [" + getLoginUrl() + "]");
            }

            //saveRequestAndRedirectToLogin(request, response);
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            //throw new Exception("访问权限不足");
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpResponse.getWriter().write("访问权限不足，请重新登录");

            return false;
        }
    }

}
