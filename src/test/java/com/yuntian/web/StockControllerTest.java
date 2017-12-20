package com.yuntian.web;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.yuntian.App;
import com.yuntian.web.api.FarmActionController;
import com.yuntian.web.api.StockController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@Slf4j
public class StockControllerTest {

	@Autowired
	private StockController stockController;

	@Autowired
	FarmActionController farmActionController;
	public StockControllerTest() {


	}


}
