package com.yuntian.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessage {

	private int errCode;
	private String errMsg;

	public ErrorMessage(int errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

}