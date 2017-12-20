package com.yuntian.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pkage")
@Getter @Setter // lombock
public class Pkage implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private long price;
	//积分,根据套餐内容计算合理成本价格，进行设置
	private long score;
	//周期-单位天
	private long cycleday;
//
//	@ManyToOne(fetch=FetchType.EAGER)
//	@JoinColumn(name="product1_id",insertable = false,updatable = false)  
//	private Product product1;
//	@ManyToOne(fetch=FetchType.EAGER)
//	@JoinColumn(name="product2_id",insertable = false,updatable = false)  
//	private Product product2;
//	@ManyToOne(fetch=FetchType.EAGER)
//	@JoinColumn(name="product3_id",insertable = false,updatable = false)  
//	private Product product3;
//    //角色 -- 权限关系：多对多关系;
//    @ManyToMany(fetch=FetchType.LAZY)
//    //@JoinColumn(name="pkage_id",insertable = false,updatable = false)  
//    @JoinTable(name="pkage_product",joinColumns={@JoinColumn(name="pkage_id")},inverseJoinColumns={@JoinColumn(name="product_id")})
//    private List<Product> Product;

    //
    @OneToMany(fetch=FetchType.EAGER,cascade={CascadeType.REMOVE})
    @JoinColumn(name="pkageid", referencedColumnName="id",insertable = false,updatable = false)  
    //@JoinTable(name="pkage_product",joinColumns={@JoinColumn(name="pkage_id")},inverseJoinColumns={@JoinColumn(name="product_id")})
    private List<Pkage_Product> pkage_Product;

	// 创建时间
	@Column(updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime = new Date();
	// 更新时间
	@Column(insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatetime = new Date();
    
}
