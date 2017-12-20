
package com.yuntian.service;


import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.yuntian.domain.Address;

public interface AddressRepository extends PagingAndSortingRepository<Address,Long>{

	List<Address> findByUserid(long id);
}
