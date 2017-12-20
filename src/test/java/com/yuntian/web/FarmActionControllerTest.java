package com.yuntian.web;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yuntian.App;
import com.yuntian.domain.Farm;
import com.yuntian.domain.FarmState;
import com.yuntian.service.FarmRepository;
import com.yuntian.web.api.FarmActionController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@Slf4j
public class FarmActionControllerTest {

	@Autowired
	private FarmRepository farmRepository;

	@Autowired
	FarmActionController farmActionController;
	public FarmActionControllerTest() {


	}

	@Test
	public void action() throws Exception {
		farmActionController.action(Long.valueOf(2), "BUG");
		assert(true);
	}



}
