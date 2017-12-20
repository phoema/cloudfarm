package com.yuntian.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class MasterPK  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String mtype ;
	public String mkey;
	public MasterPK(String mtype ,String mkey) {
        this.mtype = mtype;
        this.mkey = mkey;
    }
	public MasterPK(){
		
	}

}
