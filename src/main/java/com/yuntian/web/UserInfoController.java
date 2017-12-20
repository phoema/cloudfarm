package com.yuntian.web;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
	
	/**
	 * 用户查询.
	 * @return
	 */
	@RequestMapping("/userList")
	public String userInfo(){
		return "userInfo";
	}
	
	/**
	 * 用户添加;
	 * @return
	 */
	@RequestMapping("/userAdd")
	//@RequiresPermissions("userInfo:add")//权限管理;
	public String userInfoAdd(){
		return "userInfoAdd";
	}
	
	/**
	 * 用户删除;
	 * @return
	 */
	@RequestMapping("/userDel")
	//@RequiresPermissions("userInfo:del")//权限管理;
	public String userDel(){
		return "userInfoDel";
	}
	
}
