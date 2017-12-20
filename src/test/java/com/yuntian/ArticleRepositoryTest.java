package com.yuntian;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yuntian.domain.Article;
import com.yuntian.service.ArticleRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@Slf4j
public class ArticleRepositoryTest {

	@Autowired
	private ArticleRepository articleRepository;

	public ArticleRepositoryTest() {


	}

	@Test
	public void add() throws Exception {
		Article article = new Article();
		article.setType(2);
		article.setAuthor("phoema");
		article.setTitle("人人都有一片田");
		article.setBrowsenumber(22);
		article.setDetail("所以，面对今天这样一个传统行业数字化，跨界整合全民创业的大潮，我们所做的就是要用“互联网+”的思维，整合一个“集约化生态循环农业”基础上的订单式大农业体系，并依托云端大数据、O2O电商、微商社交等，来满足我们的用户，拥有“自主农庄，获得收益”的愿望，构建出一个巨大的跨产业、跨领域的复合商业航母，实现“人人都有一片田”的梦想！我们称它为：cloudfarm");
		articleRepository.save(article);
	}

	@Test
	public void  templist() {
		List<Article> list = (List<Article>) articleRepository.findAll();

		System.out.println("info:"+ list);
		assert(true);

	}
	@Test
	public void  findByID() {
		Article article =articleRepository.findOne(Long.valueOf(1));

		System.out.println("info:"+ article);
		assert(true);

	}
	@Test
	public void  findByType() {
		Pageable page = new PageRequest(2,1);// page start 0
		List<Article> list = articleRepository.findByType(1,page);

		System.out.println("info:"+ list);
		assert(true);

	}


}
