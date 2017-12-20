package com.yuntian.web;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yuntian.App;
import com.yuntian.domain.Score;
import com.yuntian.util.FarmException;
import com.yuntian.web.api.FarmActionController;
import com.yuntian.web.api.ScoreController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@Slf4j
public class ScoreControllerTest {

	@Autowired
	private ScoreController scoreController;

	@Autowired
	FarmActionController farmActionController;
	public ScoreControllerTest() {


	}

	@Test
	public void testProductBuy() throws FarmException{
		Score score = new Score();
		score.setUserid((long)1);
		String[] parm = {"1","5"};
		score.setParm(parm);
		scoreController.productbuy(score);
	}
}
