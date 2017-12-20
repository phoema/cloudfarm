package com.yuntian.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;

/**
 * 用户仓储库存
 * @author jiahh
 *
 */
@Entity
@Table(name = "master") @IdClass(MasterPK.class)
@Data // lombock
public class Master implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String mtype;
	@Id
	private String mkey;

	@Column(nullable = false)
	private String value;

	private int morder;

}
