package com.yuntian.domain;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 用户信息.
 * @author 
 * @version v.0.1
 */
@Entity
@Table(name = "sys_user")
//@Data // lombock
@Getter @Setter @ToString(exclude={"scorelist","roleList"})
@JsonInclude(Include.NON_NULL) //jackson null不序列化

public class SysUser implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id@GeneratedValue
    private Long uid;//用户id;
   
    @Column(unique=true)
    private String username;//账号.
    private String name;//名称（昵称或者真实姓名，不同系统不同定义）
   
    private String email;
	// 忽略password序列化
    // @JsonIgnore
    private String password; //密码;
	@Transient
    private String password2; //密码;
    private String salt;//加密密码的盐
   
    private byte state;//用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定.
	@Transient // 建库忽略
    private boolean activefarm;//是否有活动的农田
	@Transient // 建库忽略
	private List<Farm> farmlist;
	
	@OneToMany(fetch=FetchType.EAGER)
	@JsonIgnore
	@JoinColumn(name = "userid")
	private List<Score> scorelist;

	@Transient
    private long scores;//用户id;

	// 创建时间
	@JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
	@Column(updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime = new Date();
	// 更新时间
	@JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
	@Column(insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatetime = new Date();

	@ManyToMany(fetch=FetchType.EAGER)//立即从数据库中进行加载数据;
    @JoinTable(name = "sys_user_role", joinColumns = { @JoinColumn(name = "uid") }, inverseJoinColumns ={@JoinColumn(name = "roleid") })
    private List<SysRole> roleList;// 一个用户具有多个角色
   
    /**
     * 密码盐.
     * @return
     */
    public String getCredentialsSalt(){
       return this.username+this.salt;
    }
    /**
     * 获取积分合计
     * @return
     */
	public long getScores() {
		int scores = 0;
		if(scorelist != null && scorelist.size()>0){
			for(Score score : scorelist){
				scores+=score.getScore();
			}
		}
		return scores;
	}
  
}