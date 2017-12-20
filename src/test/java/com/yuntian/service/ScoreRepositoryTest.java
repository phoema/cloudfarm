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
	@Test
	public void  find() {

		//Score score = scoreRepository.findByUseridAndNameAndCreateTimeLike((long)1,"签到","2016-08-03");
		List<Score> list = scoreRepository.findByuserid((long)1);
		System.out.println("info:"+ list);
		assert(true);

	}
	@Test
	public void  find2() {

		//Score score = scoreRepository.findByUseridAndNameAndCreateTimeLike((long)1,"签到","2016-08-03");
		Score score= scoreRepository.findByUseridAndNameAndCreateday((long)1,"签到",20160803);
		System.out.println("info:"+ score);
		assert(true);

	}


}
