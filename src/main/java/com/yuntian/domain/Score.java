package com.yuntian.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 用户积分履历
 * @author jiahh
 *
 */
@Entity
@Table(name = "score")
//@Data // lombock
@Getter @Setter @ToString(exclude="user")
public class Score implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;

	private String note;

	@Column(nullable = false)
	private Long score;

	@Column(name="userid")
	public Long userid;
	
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
	@Transient //根据不用业务情况，存储不同参数数据
	private String[] parm;

	@JsonIgnore
	@Transient
	SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");

	// 创建时间
	@Column(updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime = new Date();
	// 更新时间
	@Column(insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatetime = new Date();
	
	private Long createday;
	
	public Long getCreateday(){
		if(createday == null && createtime != null){
			return Long.parseLong(dateFormater.format(createtime));
		}
		else return createday;
		
	}
	public void setCreatetime(Date createtime){
		this.createtime = createtime;
		if(createtime != null){
			this.createday = Long.parseLong(dateFormater.format(createtime));
		}
		
	}

}
