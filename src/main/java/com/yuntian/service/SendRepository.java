
package com.yuntian.service;


import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.yuntian.domain.Send;

public interface SendRepository extends PagingAndSortingRepository<Send,Long>{

	List<Send> findByUserid(long id);
}
