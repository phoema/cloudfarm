package com.yuntian.web.api;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.IncorrectCredentialsException;
//import org.apache.shiro.authc.UnknownAccountException;
//import org.apache.shiro.session.Session;
//import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.yuntian.domain.Farm;
import com.yuntian.domain.FarmState;
import com.yuntian.domain.SysRole;
import com.yuntian.domain.SysUser;
import com.yuntian.service.FarmRepository;
import com.yuntian.util.FarmUtils;
import com.yuntian.web.CommonController;

@RestController
public class HomeController  extends CommonController{
    @Autowired
    private FarmRepository farmRepository;
    @Autowired
	JacksonProperties pro;

	@RequestMapping("/test")
	public String test(){

//		ObjectMapper mapper = new ObjectMapper();  
//		PropertyNamingStrategy p = mapper.getPropertyNamingStrategy();
//		SerializationConfig s = mapper.getSerializationConfig();
//		pro.getParser();
		return "index";
	}
	
	// 登录提交地址和applicationontext-shiro.xml配置的loginurl一致。 (配置文件方式的说法)
		@RequestMapping(value="/api/login",method=RequestMethod.POST)
		public SysUser login(HttpServletRequest request, Map<String, Object> map) throws Exception {
			SysUser user = null;
			String msg = null;
			String username = request.getParameter("username");  
	        String password = request.getParameter("password");  
	        boolean rememberMe = false;
	        Subject subject = SecurityUtils.getSubject();  
	        UsernamePasswordToken token = new UsernamePasswordToken(username, password); 
	        token.setRememberMe(rememberMe);
	        try {  
	            subject.login(token);  
	    		user = (SysUser)BeanUtils.cloneBean(subject.getPrincipal());
	    		user.setPassword(null);
	    		user.setRoleList(null);
	    		// 判断是否有活动的农田
	    		List<Farm> farmlist = farmRepository.findByuser_uidOrderByCreatetimeDesc(user.getUid());
	    		user.setFarmlist(farmlist);
	    		for(Farm farm : farmlist){
	    			if(farm.getState() != FarmState.CLOSED){
	    				user.setActivefarm(true);
	    				break;
	    			}
	    		}

	        } catch (UnknownAccountException ex) {  
				System.out.println("exception=" + ex);
	        	msg = "用户名/密码错误";
	        } catch(Exception ex){
				System.out.println("exception=" + ex);
	        	msg = "用户名/密码错误";
	        }

			if(!Strings.isNullOrEmpty(msg)){
				throw new Exception(msg);
			}
			// 此方法不处理登录成功,由shiro进行处理.
    		ObjectMapper mapper = new ObjectMapper();
    		String userstr = mapper.writeValueAsString(user);
			return user;
		}
		@RequestMapping(value="/api/adminlogin",method=RequestMethod.POST)
		public SysUser adminlogin(HttpServletRequest request, Map<String, Object> map) throws Exception {
			SysUser user = this.login(request, map);
            // 获取用户信息
	        user = (SysUser)FarmUtils.getCurUser();

			List<SysRole> rolelist =user.getRoleList();
			boolean isadmin = false;
			for(SysRole role : rolelist){
				if("admin".equals(role.getRole())){
					isadmin = true;
				}
			}
			if(isadmin){
				return user;
			}else{
				throw new Exception("用户没有管理员权限");
			}
		}

		@RequestMapping(value="/api/logout",method=RequestMethod.GET)
		public String loginout(){
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.logout();
			return "logout";
		}
	
}
