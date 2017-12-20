package com.yuntian.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.yuntian.util.ErrorMessage;
import com.yuntian.util.FarmException;

public class CommonController {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody String greetingExceptionHandler(Exception ex) {
		
		System.out.println("@greetingExceptionHandler:" + this.getClass().getSimpleName() + "---Exception:" + ex.getMessage());
		return ex.getMessage();
	}

	@ExceptionHandler(FarmException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody String farmExceptionHandler(FarmException ex) {
		
		System.out.println("@farmExceptionHandler:" + this.getClass().getSimpleName() + "---Exception:" + ex.getMessage());
		return ex.getMessage();
	}

}