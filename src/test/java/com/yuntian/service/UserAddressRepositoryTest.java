package com.yuntian.service;

import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yuntian.App;
import com.yuntian.domain.Article;
import com.yuntian.domain.Address;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@Slf4j
public class UserAddressRepositoryTest {

	@Autowired
	private AddressRepository addressRepository;

	public UserAddressRepositoryTest() {


	}

	@Test
	public void add() throws Exception {
		Address address = new Address();
		address.setUserid((long)2);
		address.setAddress("海淀区西外太平庄55号知识产权出版社");
		addressRepository.save(address);
	}

	@Test
	public void  templist() {
		List<Address> list = (List<Address>) addressRepository.findAll();

		System.out.println("info:"+ list);
		assert(true);

	}
	@Test
	public void  findByID() {
		Address article =addressRepository.findOne(Long.valueOf(1));

		System.out.println("info:"+ article);
		assert(true);

	}
	@Test
	public void  findByUserid() {
		List<Address> list = addressRepository.findByUserid(2);

		System.out.println("info:"+ list);
		assert(true);

	}


}
