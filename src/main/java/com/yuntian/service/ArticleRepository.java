
package com.yuntian.service;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.yuntian.domain.Article;

public interface ArticleRepository extends PagingAndSortingRepository<Article,Long>{

	List<Article> findByType(int type,Pageable page);
}
