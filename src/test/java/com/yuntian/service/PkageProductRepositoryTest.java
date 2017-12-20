package com.yuntian.service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yuntian.App;
import com.yuntian.domain.Pkage_Product;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@Slf4j
public class PkageProductRepositoryTest {

	@Autowired
	private PkageProductRepository repository;

	public PkageProductRepositoryTest() {


	}

	@Test
	public void test() throws Exception {
	}

	@Test
	public void  findByProductid() {
		List<Pkage_Product> list = repository.findByProduct_id(Long.valueOf(2));
		System.out.println("list--" + list);
		assert(true);
	}	


}
