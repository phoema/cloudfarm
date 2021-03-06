package com.yuntian.service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yuntian.App;
import com.yuntian.domain.Score;
import com.yuntian.domain.StockHistory;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@Slf4j
public class StockHistoryRepositoryTest {

	@Autowired
	private StockHistoryRepository stockHistoryRepository;

	public StockHistoryRepositoryTest() {


	}

	@Test
	public void test() throws Exception {
	}

	@Test
	public void  save() {
		StockHistory stokcHis = new StockHistory();
		stokcHis.setOpertype("收获");
		stokcHis.setProductid((long)1);
		stokcHis.setStock((long)20);
		stockHistoryRepository.save(stokcHis);
		assert(true);

	}	



}
