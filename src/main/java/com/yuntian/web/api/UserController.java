package com.yuntian.web.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yuntian.domain.SysUser;
import com.yuntian.service.SysUserRepository;
import com.yuntian.util.FarmException;
import com.yuntian.util.FarmUtils;
import com.yuntian.web.CommonController;

@RestController
@RequestMapping(value="/api/user",method={RequestMethod.POST,RequestMethod.GET})
public class UserController extends CommonController {
    @Autowired
    private SysUserRepository userInfoRepository;

    @RequestMapping("/save")
    public SysUser save(@RequestBody SysUser user) throws Exception {
    	try{
    		SysUser dbuser = userInfoRepository.findOne(user.getUid());
    		//  根据业务将页面参数置换数据库内容
    		dbuser.setEmail(user.getEmail());
    		dbuser.setName(user.getName());
    		dbuser.setUsername(user.getUsername());
    		dbuser.setPassword(user.getPassword());
    		user = userInfoRepository.save(user);
    	}catch (Exception ex){
    		if(ex.getClass() == DataIntegrityViolationException.class){
    			throw new FarmException("用户名违反唯一性约束");
    		}else
    			throw ex;
    	}
        return user;
    }
    @RequestMapping("/get")
    public SysUser get(String username) {
    	SysUser user = userInfoRepository.findByUsername(username);
        return user;
    }
    @RequestMapping("/list")
    public List<SysUser> list() {
    	List<SysUser> user = (List<SysUser>)userInfoRepository.findAll();
        return user;
    }
    /**
     * 更新用户资料
     * @param user
     * @return
     */
    @RequestMapping("/update")
    @Transactional
    public SysUser updateInfo(@RequestBody SysUser user) {
    	
    	int ret = 0;
    	if(user.getUid() != 0){
    		SysUser olduser = userInfoRepository.findOne(user.getUid());
    		olduser.setEmail(user.getEmail());
    		olduser.setName(user.getName());
    		olduser.setUsername(user.getUsername());
    		if(user.getPassword()!= null){
    			olduser.setPassword(user.getPassword());
    		}
    		ret = userInfoRepository.updateUserWithUsernameAndNameAndEmail(user.getName(), user.getUsername(), user.getEmail(), user.getUid());
    		user = olduser;
    	}else{
    		user = userInfoRepository.save(user);
    	}
        return user;
    }

    /**
     * 删除指定用户
     * @param uid
     * @return
     */
    @RequestMapping("/delete")
    public boolean delete(long uid) {
    	userInfoRepository.delete(Long.valueOf(uid));
        return true;
    }
    /**
     * 删除指定一组用户
     * @param uid
     * @return
     */
   @RequestMapping("/deletes")
    public boolean deletes(Long[] uids) {
    	List<SysUser> userlist = new ArrayList<SysUser>();
    	for(Long uid : uids){
    		SysUser user = new SysUser();
    		user.setUid(uid);
    		userlist.add(user);
    	}
    	userInfoRepository.delete(userlist);
        return true;
    }
    
   @RequestMapping("/curuser")
   public SysUser curuser() {
		SysUser curuser = (SysUser)FarmUtils.getCurUser();
		curuser.setPassword(null);
       return curuser;
   }

   /**
    * 更新用户名
    * @param user
    * @return
    * @throws FarmException
    */
	@RequestMapping("/updatemyname")
	public SysUser updateName(@RequestBody SysUser user) throws FarmException {
		String name = user.getName();
		SysUser curuser = (SysUser) FarmUtils.getCurUser();
		if (curuser.getUid() != user.getUid()) {
			throw new FarmException("登录用户与待修改用户不匹配");
		}

		user = userInfoRepository.findOne(user.getUid());
		user.setName(name);
		userInfoRepository.save(user);
		return null;
	}

   /**
    * 更新用户邮箱
    * @param user
    * @return
    * @throws FarmException
    */
	@RequestMapping("/updatemyemail")
	public SysUser updateEmail(@RequestBody SysUser user) throws FarmException {

		String email = user.getEmail();
		SysUser curuser = (SysUser) FarmUtils.getCurUser();
		if (curuser.getUid() != user.getUid()) {
			throw new FarmException("登录用户与待修改用户不匹配");
		}

		user = userInfoRepository.findOne(user.getUid());
		user.setEmail(email);
		userInfoRepository.save(user);
		return null;
	}

	   /**
	    * 更新用户密码
	    * @param user
	    * @return
	    * @throws FarmException
	    */
	@RequestMapping("/updatemypwd")
	public SysUser updatePassword(@RequestBody SysUser user)
			throws FarmException {

		// 老密码
		String password = user.getPassword();
		// 新密码
		String password2 = user.getPassword2();
		SysUser curuser = (SysUser) FarmUtils.getCurUser();
		if (curuser.getUid() != user.getUid()) {
			throw new FarmException("登录用户与待修改用户不匹配");
		}

		user = userInfoRepository.findOne(user.getUid());
		if (!password.equals(user.getPassword())) {
			throw new FarmException("原密码输入错误");
		}

		user.setPassword(password2);
		userInfoRepository.save(user);
		return null;
	}

}