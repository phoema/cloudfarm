package com.yuntian.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class StockPK  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Long productid ;
	public Long userid;
	public StockPK(long userid ,long productid) {
        this.productid = productid;
        this.userid = userid;
    }
	public StockPK(){
		
	}

}
