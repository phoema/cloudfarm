package com.yuntian.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 用户配送地址信息
 * @author jiahh
 *
 */
@Entity
@Table(name = "send")
@Getter @Setter // lombock
public class Send implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(name="userid")
	private Long userid;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	private Long stock;

	private Long productid;
	private String productname;
	private String producttype;
	// 配送明细
	private String detail;
	// 配送状态
	private SendState state;

	@ManyToOne  
	@JoinColumn(name="productid",insertable = false,updatable = false)  
	@JsonIgnore
	private Product product;
	@ManyToOne  
	@JoinColumn(name="userid",insertable = false,updatable = false)  
	@JsonIgnore
	private SysUser user;
	// 忽略username映射
	@Transient
	String username;
	public String getUsername() {
		if(user != null)
			return user.getUsername();
		else return username;
	}

	// 创建时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime = new Date();
	// 更新时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatetime = new Date();

	
}
