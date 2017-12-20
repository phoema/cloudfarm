package com.yuntian.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户配送地址信息
 * @author jiahh
 *
 */
@Entity
@Table(name = "address")
@Getter @Setter // lombock
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(name="userid")
	private Long userid;

	@Column(nullable = false)
	private String address;
	
}
