package com.yuntian.web.api;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.IncorrectCredentialsException;
//import org.apache.shiro.authc.UnknownAccountException;
//import org.apache.shiro.session.Session;
//import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yuntian.domain.Article;
import com.yuntian.service.ArticleRepository;
import com.yuntian.web.CommonController;

@RestController
@RequestMapping(value="/api/article",method={RequestMethod.POST,RequestMethod.GET})
public class ArticleController  extends CommonController{
    @Autowired
    private ArticleRepository articleRepository;

    @RequestMapping("/save")
    public Article save(@RequestBody Article article) throws Exception {
		if(article.getId() == null){
			article.setCreatetime(new Date());
		}else{
			Article dbvalue = articleRepository.findOne(article.getId());
			if(dbvalue != null){
				dbvalue.setTitle(article.getTitle());
				dbvalue.setDetail(article.getDetail());
				dbvalue.setType(article.getType());
				article.setUpdatetime(new Date());
				article = dbvalue;
			}else{
				throw new Exception("指定文章不存在");
			}
		}
    	article = articleRepository.save(article);
        return article;
    }
    @RequestMapping("/get")
    public Article get(Long id) {
    	
    	Article article = articleRepository.findOne(id);
    	// 访问一次，浏览量++
    	article.setBrowser(article.getBrowser() + 1);
    	article = articleRepository.save(article);
    	
        return article;
    }
    @RequestMapping("/getbytypepage")
    public List<Article> getbytype(int type,Pageable page) {
		//Pageable page = new PageRequest(2,1);// page start 0
    	List<Article> articles = articleRepository.findByType(type, page);
        return articles;
    }
    @RequestMapping("/getbytype")
    public List<Article> getbytype(int type) {
    	List<Article> articles = articleRepository.findByType(type,null);
    	// 去除detail字段
    	for(Article article : articles){
    		article.setDetail(null);
    	}
        return articles;
    }
    @RequestMapping("/list")
    public List<Article> getall() {
    	List<Article> articles = (List<Article>)articleRepository.findAll();
    	// 去除detail字段
    	for(Article article : articles){
    		article.setDetail(null);
    	}
        return articles;
    }
    /**
     * 暂时不用
     */
    @RequestMapping("/browserplus")
    private Article browserplus(long id) {
    	Article article = articleRepository.findOne(id);
    	article.setBrowser(article.getBrowser() + 1);
    	article = articleRepository.save(article);
        return article;
    }
 
}
