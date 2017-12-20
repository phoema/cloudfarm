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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
 
 
/**
 * 系统角色实体类;
 * @author Angel(QQ:412887952)
 * @version v.0.1
 */
@Entity
@Table(name = "sys_role")
//@Data // lombock
@Getter @Setter @ToString(exclude="permissions")
public class SysRole implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id@GeneratedValue
    private Long id; // 编号
    private String role; // 角色标识程序中判断使用,如"admin",这个是唯一的:
    private String description; // 角色描述,UI界面显示使用
    private Boolean available = Boolean.FALSE; // 是否可用,如果不可用将不会添加给用户
   
    //角色 -- 权限关系：多对多关系;
    @OneToMany(fetch=FetchType.EAGER)
    @JoinTable(name="sys_role_permission",joinColumns={@JoinColumn(name="roleid")},inverseJoinColumns={@JoinColumn(name="permissionid")})
    private List<SysPermission> permissions;

	// 创建时间
	@Column(updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime = new Date();
	// 更新时间
	@Column(insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatetime = new Date();

}
 