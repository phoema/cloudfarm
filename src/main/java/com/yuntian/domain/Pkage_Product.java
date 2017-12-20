package com.yuntian.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pkage_product")
@Getter @Setter // lombock
public class Pkage_Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
//	private Long pkageid;
//	private Long productid;

	private Long price_cost;
	// 套餐内某产品保底产量--应当以产品保底产量作为单位进行换算
	@Column(nullable = false)
	private long min;
	// 套餐内某产品最大产量--应当以产品最大产量作为单位进行换算
	@Column(nullable = false)
	private long max;
	//套餐内某产品真实产量字段   关联农田的各种属性进行计算得出 (max-min)*(A+B+C+D)/400
//	@Transient
//	private long real;
	private Long pkageid;
	@ManyToOne
	@JoinColumn(name="pkageid",insertable = false,updatable = false)
	@JsonIgnore
	private Pkage pkage;
	@ManyToOne
	@JoinColumn(name="productid")
	private Product product;
	
}
