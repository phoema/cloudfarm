package com.yuntian.service;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.yuntian.domain.SysUser;
 
/**
 * UserInfo持久化类;
 * @author Angel(QQ:412887952)
 * @version v.0.1
 */
public interface SysUserRepository extends CrudRepository<SysUser,Long>{
   
    /**通过username查找用户信息;*/
    public SysUser findByUsername(String username);
    
    /**
     * 通过uid更新用户信息
     * @param name
     * @param username
     * @param email
     * @param uid
     * @return
     */
    @Modifying
    @Query("update SysUser u set u.name = :name,u.username=:username,email=:email where u.uid = :uid")
    public int updateUserWithUsernameAndNameAndEmail(@Param("name") String name, @Param("username") String username, @Param("email") String email, @Param("uid") long uid);   
}