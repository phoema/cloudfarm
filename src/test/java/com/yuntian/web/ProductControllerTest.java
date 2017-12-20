package com.yuntian.web;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yuntian.App;
import com.yuntian.util.FarmException;
import com.yuntian.web.api.FarmActionController;
import com.yuntian.web.api.ProductController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@Slf4j
public class ProductControllerTest {

	@Autowired
	private ProductController productController;

	public ProductControllerTest() {


	}

	@Test
	public void delete() throws FarmException{
		productController.delete(Long.valueOf(1));
	}
}
