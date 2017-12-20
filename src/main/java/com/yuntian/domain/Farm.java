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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yuntian.util.FarmUtils;

/**
 * 用户农田信息
 * @author jiahh
 *
 */
@Entity
@Table(name = "farm")
@Getter @Setter // lombock
public class Farm implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Long pkageid;

	@ManyToOne(fetch=FetchType.EAGER)//立即从数据库中进行加载数据;
	@JoinColumn(name="pkageid",insertable = false,updatable = false)  
	// 忽略user序列化
	//@JsonIgnore
	private Pkage pkage;
	@Transient
	private List<Long> reallist;;

	@Column(name="userid")
	private Long userid;
	
	@ManyToOne  
	@JoinColumn(name="userid",insertable = false,updatable = false)  
	// 忽略user序列化
	@JsonIgnore
	private SysUser user;
	// 忽略username映射
	@Transient
	String username;

	public String getUsername() {
		if(this.username == null && this.user != null){
			username = user.getUsername();
		}
		return this.username;
	}

	public String getState_cn(){
		if(this.state != null && this.state_cn == null){
			this.state_cn = FarmUtils.statedict.get(this.state.toString());
		}
		return this.state_cn;
	}
	private long shirundu;

	private long feiwodu;

	private long haichongdu;

	private long zacaodu;

	private FarmState state;
	// 创建时间
	@Column(updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime = new Date();
	// 更新时间
	@Column(insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatetime = new Date();
	
	// 农田状态-翻译
	@Transient
	private String state_cn;

}
