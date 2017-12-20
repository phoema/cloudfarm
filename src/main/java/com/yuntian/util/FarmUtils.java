package com.yuntian.util;

import java.util.Hashtable;


public class FarmUtils {

	public static Hashtable<String, String> statedict = new Hashtable<String, String>();
	static {
		statedict.put("1", "播种期");
		statedict.put("2", "萌芽期");
		statedict.put("3", "生长期");
		statedict.put("4", "结果期");
		statedict.put("5", "收获期");
		statedict.put("6", "已关闭");
	}    
	static {
		statedict.put("SEED", "播种期");
		statedict.put("BUD", "萌芽期");
		statedict.put("GROW", "生长期");
		statedict.put("FRUIT", "结果期");
		statedict.put("HARVEST", "收获期");
		statedict.put("CLOSED", "已关闭");
	}    
	public static Hashtable<String, String> articletype = new Hashtable<String, String>();
	static {
		articletype.put("0", "未分类");
		articletype.put("1", "云田公益");
		articletype.put("2", "云创空间");
	}    

	/**
	 * 获取当前用户 
	 * @return
	 */
	public static Object  getCurUser(){
    	// 获取当前用户
		org.apache.shiro.subject.Subject currentUser = org.apache.shiro.SecurityUtils.getSubject();
		//(SysUser)
		return currentUser.getPrincipal();
		
	}
}