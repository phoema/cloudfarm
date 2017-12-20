package com.yuntian.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 用户仓储库存
 * @author jiahh
 *
 */
@Entity
@Table(name = "stockhistory")
@Data // lombock
public class StockHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
//    @EmbeddedId
//    private StockPK id;
//	@Id
	@Column(name="productid")
	private Long productid;
//	@Id
	@Column(name="userid")
	private Long userid;

	@Column(nullable = false)
	private Long stock;
	@Column(nullable = false)
	private String opertype;
	private String note;

	private String productname;
	private String producttype;

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
	@Column(updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime = new Date();
	// 更新时间
	@Column(insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatetime = new Date();

}
