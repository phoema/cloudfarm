package com.yuntian.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Shiro 配置
 * 
Apache Shiro 核心通过 Filter 来实现，就好像SpringMvc 通过DispachServlet 来主控制一样。 
既然是使用 Filter 一般也就能猜到，是通过URL规则来进行过滤和权限校验，所以我们需要定义一系列关于URL的规则和访问权限。
 * 
 * @author Angel(QQ:412887952)
 * @version v.0.1
 */
@Configuration 
public class ShiroConfiguration {
		
	
	/**
	 * ShiroFilterFactoryBean 处理拦截资源文件问题。
	 * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
	 * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
	 * 
	 	Filter Chain定义说明 
		1、一个URL可以配置多个Filter，使用逗号分隔 
		2、当设置多个过滤器时，全部验证通过，才视为通过 
		3、部分过滤器可指定参数，如perms，roles
	 * 
	 */
	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager){
		System.out.println("ShiroConfiguration.shirFilter()");
		ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();
		
		 // 必须设置 SecurityManager  
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		
		Map<String,Filter> maps = shiroFilterFactoryBean.getFilters();
		//maps.put("myRestAuthc", myRestFilter());
		maps.put("myFormFilter", myFormFilter());
		shiroFilterFactoryBean.setFilters(maps);
		Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
		
		//配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/logout", "logout");
		// 匿名访问
		filterChainDefinitionMap.put("/api/login", "anon");
		filterChainDefinitionMap.put("/api/logout", "anon");
		filterChainDefinitionMap.put("/api/article/get", "anon");
		filterChainDefinitionMap.put("/api/article/getbytype", "anon");
		filterChainDefinitionMap.put("/api/article/getbytypepage", "anon");
		filterChainDefinitionMap.put("/api/article/list", "anon");
		filterChainDefinitionMap.put("/api/user/save", "anon");
		filterChainDefinitionMap.put("/api/pkage/list", "anon");

		filterChainDefinitionMap.put("/api/user/curuser", "myFormFilter");
		filterChainDefinitionMap.put("/api/user/updatemyemail", "myFormFilter");
		filterChainDefinitionMap.put("/api/user/updatemyname", "myFormFilter");
		filterChainDefinitionMap.put("/api/user/updatemypwd", "myFormFilter");
		
		// admin访问
		filterChainDefinitionMap.put("/api/article/save", "roles[admin]");
		filterChainDefinitionMap.put("/api/article/delete", "roles[admin]");
		filterChainDefinitionMap.put("/api/user/get", "roles[admin]");
		filterChainDefinitionMap.put("/api/user/delete", "roles[admin]");
		filterChainDefinitionMap.put("/api/user/list", "roles[admin]");
		filterChainDefinitionMap.put("/api/user/update", "roles[admin]");
		filterChainDefinitionMap.put("/api/user/deletes", "roles[admin]");
		filterChainDefinitionMap.put("/manage/login.html", "anon");
		filterChainDefinitionMap.put("/manage/**.html", "roles[admin]");
		
		//<!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
	    //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
//		filterChainDefinitionMap.put("/assets/**", "anon");
//		filterChainDefinitionMap.put("/**.html", "anon");
//		filterChainDefinitionMap.put("/api/user/curuser", "anon");
//		filterChainDefinitionMap.put("/api/user/list", "roles[admin]");
//		filterChainDefinitionMap.put("/api/login", "anon");
//		filterChainDefinitionMap.put("/api/**", "authc");
		//filterChainDefinitionMap.put("/api/**", "myRestAuthc");
//		filterChainDefinitionMap.put("/**", "anon");
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/index.html");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index.html");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/login.html");
		
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}
	
	
//	@Bean
//	public MyRestFilter myRestFilter(){
//		MyRestFilter myRestFilter =  new MyRestFilter();
//		return myRestFilter;
//	}
	@Bean
	public MyFormFilter myFormFilter(){
		MyFormFilter myFormFilter =  new MyFormFilter();
		return myFormFilter;
	}
	
	@Bean
	public SecurityManager securityManager(){
		DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
		//设置realm.
		securityManager.setRealm(myShiroRealm());
		return securityManager;
	}
	
	
	/**
	 * 身份认证realm;
	 * (这个需要自己写，账号密码校验；权限等)
	 * @return
	 */
	@Bean
	public MyShiroRealm myShiroRealm(){
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		//MyShiroRealm 加密存储时，打开凭证匹配
		//myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());;
		return myShiroRealm;
	}
	
	/**
	 * 凭证匹配器
	 * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
	 *  所以我们需要修改下doGetAuthenticationInfo中的代码;
	 * ）
	 * @return
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher(){
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		
		hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(1);//散列的次数，比如散列两次，相当于 md5(md5(""));
		
		return hashedCredentialsMatcher;
	}
	
	/**
	 *  开启shiro aop注解支持.
	 *  使用代理方式;所以需要开启代码支持;
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
	
}
