package com.yuntian.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

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

/**
 * 用户农田操作履历
 * @author jiahh
 *
 */
@Entity
@Table(name = "farmaction")
@Getter @Setter // lombock
public class FarmAction implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private Long farmid;

	//land water feed  bug
	@Column(nullable = false)//yyyymmdd
	private String action;
	
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
	
	@ManyToOne(fetch=FetchType.EAGER)//立即从数据库中进行加载数据;
	@JoinColumn(name="farmid",insertable = false,updatable = false)  
	// 忽略user序列化
	//@JsonIgnore
	private Farm farm;

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
