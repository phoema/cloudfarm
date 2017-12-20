package com.yuntian.domain;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

/**
* 权限实体类;
* @author Angel(QQ:412887952)
* @version v.0.1
*/
@Entity
@Table(name = "sys_permission")
@Data // lombock
public class SysPermission implements Serializable{
   private static final long serialVersionUID = 1L;
  
   @Id
   @GeneratedValue
   private long id;//主键.
   private String name;//名称.
  
   @Column(columnDefinition="enum('menu','button')")
   private String resourceType;//资源类型，[menu|button]
   private String url;//资源路径.
   private String permission; //权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
   private Long parentId; //父编号
   private String parentIds; //父编号列表
   private Boolean available = Boolean.FALSE;

	// 创建时间
	@Column(updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime = new Date();
	// 更新时间
	@Column(insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatetime = new Date();

}