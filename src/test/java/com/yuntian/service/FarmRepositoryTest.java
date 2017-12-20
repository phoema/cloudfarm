package com.yuntian.service;

import java.util.Date;
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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@Slf4j
public class FarmRepositoryTest {

	@Autowired
	private FarmRepository farmRepository;

	public FarmRepositoryTest() {


	}

	@Test
	public void add() throws Exception {
		Farm farm = new Farm();
		farm.setName("我的农田");
		farm.setState(FarmState.GROW);
		farm.setUserid(Long.valueOf(1));
		farm.setPkageid(Long.valueOf(1));
		farmRepository.save(farm);
	}
	@Test
	public void update() throws Exception {
		Farm farm = new Farm();
		farm.setId(Long.valueOf(13));
		farm.setName("我的农田");
		farm.setState(FarmState.BUD);
		farm.setUserid(Long.valueOf(1));
		farm.setPkageid(Long.valueOf(2));
		farmRepository.save(farm);
	}

	@Test
	public void  myfarms() {
		List<Farm> list = (List<Farm>) farmRepository.findByuser_uidOrderByCreatetimeDesc(Long.valueOf(1));

		System.out.println("info:"+ list);
		assert(true);

	}
	@Test
	public void  findByID() {
		Farm farm =farmRepository.findOne(Long.valueOf(2));

		System.out.println("info:"+ farm);
		assert(true);

	}
	@Test
	public void  findAll() {
		//Pageable page = new PageRequest(2,1);// page start 0
		List<Farm> list = (List<Farm>) farmRepository.findAll();

		System.out.println("info:"+ list);
		assert(true);

	}


}
