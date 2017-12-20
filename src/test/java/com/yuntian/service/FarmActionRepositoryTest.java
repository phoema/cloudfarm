package com.yuntian.service;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yuntian.App;
import com.yuntian.domain.FarmAction;
import com.yuntian.service.FarmActionRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@Slf4j
public class FarmActionRepositoryTest {

	@Autowired
	private FarmActionRepository farmoperRepository;

	public FarmActionRepositoryTest() {


	}

	@Test
	public void  find2() {
		//Pageable page = new PageRequest(2,1);// page start 0
		FarmAction farm =  farmoperRepository.findByFarmidAndActionAndCreateday(Long.valueOf(1), "BUG", 20160803);

		System.out.println("info:"+ farm);
		assert(true);

	}


}
