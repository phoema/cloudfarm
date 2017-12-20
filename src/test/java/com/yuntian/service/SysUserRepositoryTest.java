package com.yuntian.service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yuntian.App;
import com.yuntian.domain.SysPermission;
import com.yuntian.domain.SysRole;
import com.yuntian.domain.SysUser;
import com.yuntian.service.SysPermissionRepository;
import com.yuntian.service.SysRoleRepository;
import com.yuntian.service.SysUserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@Slf4j
public class SysUserRepositoryTest {

	@Autowired
	private SysUserRepository userInfoService;
    @Autowired
    private SysRoleRepository sysRoleRepository;
    @Autowired
    private SysPermissionRepository sysPermissionRepository;

	public SysUserRepositoryTest() {


	}

	/**
	 * 创建内存表
	 * 
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
	}

	@Test
	public void  tempuserlist() {
		SysUser userInfo = userInfoService.findByUsername("admin");
		System.out.println("userInfo:"+ userInfo.toString());
		String info = userInfo.getPassword();
		System.out.println("info:"+ info);
		assert(true);

	}
	@Test
	public void  temprolelist() {
		List<SysRole> list = (List<SysRole>) sysRoleRepository.findAll();
		System.out.println("SysRoleList:"+ list);
		assert(true);

	}
	@Test
	public void  temppermissionlist() {
		List<SysPermission> list = (List<SysPermission>) sysPermissionRepository.findAll();
		System.out.println("SysPermissionList:"+ list);
		assert(true);
	}


}
