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
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

/**
 * 农产品信息
 * @author jiahh 2016年8月11日
 *
 */
@Entity
@Table(name = "product")
@Getter @Setter // lombock
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;
	// 品种--津优35
	private String type;
	// 种植标准
	private String standard;

	@Column(nullable = false)
	private String note;

	@Column(nullable = false)
	private long price;
	// 积分换购 每kg消耗积分
	private long score;
	// 单位保底产量 KG
	@Column(nullable = false)
	private long min;
	// 单位最大产量KG
	@Column(nullable = false)
	private long max;
//	// 实际产量 虚拟列
//	@Transient
//	private long real;
	// 产品图片
	private String url;
	
	// 创建时间
	@Column(updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime = new Date();
	// 更新时间
	@Column(insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatetime = new Date();

}
