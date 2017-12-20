package com.yuntian.web;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yuntian.App;
import com.yuntian.domain.Send;
import com.yuntian.util.FarmException;
import com.yuntian.web.api.SendController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@Slf4j
public class SendControllerTest {

	@Autowired
	private SendController sendController;

	public SendControllerTest() {


	}

	@Test
	public void testsaves() throws FarmException{
		Send send = new Send();
		send.setUserid((long)2);
		send.setProductid((long)1);
		send.setAddress("地址1");
		send.setStock((long)20);
		Send his = sendController.save(send);
		System.out.println();
	}

	@Test
	public void testget(){
		long uid = 1;

		List<Send> list =sendController.getbyuserid(uid);
		System.out.println();
	}

}
