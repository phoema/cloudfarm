package com.yuntian;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yuntian.domain.Score;
import com.yuntian.service.ScoreRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@Slf4j
public class ScoreRepositoryTest {

	@Autowired
	private ScoreRepository scoreRepository;

	public ScoreRepositoryTest() {


	}

	@Test
	public void test() throws Exception {
	}

	@Test
	public void  templist() {
		List<Score> list = (List<Score>) scoreRepository.findAll();

		System.out.println("info:"+ list);
		assert(true);

	}


}
