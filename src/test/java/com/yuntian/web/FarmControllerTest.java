package com.yuntian.web;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yuntian.App;
import com.yuntian.domain.FarmState;
import com.yuntian.domain.Farm;
import com.yuntian.web.api.FarmActionController;
import com.yuntian.web.api.FarmController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@Slf4j
public class FarmControllerTest {

	@Autowired
	private FarmController farmController;

	@Autowired
	FarmActionController farmActionController;
	public FarmControllerTest() {


	}

	/**
	 * 收获农田产品入仓库
	 * @throws Exception
	 */
	//@Transactional
	@Test
	public void getfromfarm() throws Exception {
		long farmid = 2;
		Farm farm = farmController.get(farmid);
		farm.setState(FarmState.HARVEST);
		farmController.save(farm);
		farmController.harvest(farmid);
		assert(true);
	}



}
