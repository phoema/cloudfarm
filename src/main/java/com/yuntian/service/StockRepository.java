/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yuntian.service;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.yuntian.domain.Stock;
import com.yuntian.domain.StockPK;
import com.yuntian.domain.SysUser;

public interface StockRepository extends CrudRepository<Stock,StockPK>{

    /**通过username查找仓库入库信息;*/
	List<Stock> findByUser(SysUser user);
    /**通过userid查找仓库入库信息;*/
	List<Stock> findByUser_uid(long uid);
	/**通过userid查找仓库入库信息并按产品分组;*/
	@Modifying
	@Query("select t.productid as productid ,sum(t.stock) as stock from Stock t where t.userid =?1 group by t.productid")
//	@Query("select sum(t.stock) as stock ,t.productid,t2.name,t2.type from Stock t"
//			+" left join Product t2 on t.productid=t2.id "
//			+" where t.userid=?1 group  by t.productid")
	List<Object[]> groupByUser_uid(long uid);

}
