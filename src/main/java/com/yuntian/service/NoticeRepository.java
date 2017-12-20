
package com.yuntian.service;


import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.yuntian.domain.Article;
import com.yuntian.domain.Notice;

public interface NoticeRepository extends PagingAndSortingRepository<Notice,Long>{

	List<Notice> findByStatus(boolean status);
}
