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

import org.springframework.data.repository.CrudRepository;

import com.yuntian.domain.Farm;
import com.yuntian.domain.FarmState;
import com.yuntian.domain.SysUser;

public interface FarmRepository extends CrudRepository<Farm,Long>{

    /**通过username查找用户信息;*/
	List<Farm> findByuser_uidOrderByCreatetimeDesc(Long user_id);
	List<Farm> findByUser(SysUser user);

	// 状态不为关闭的所有农田
	List<Farm> findByStateNot(FarmState state);
	// 状态农田
	List<Farm> findByState(FarmState state);
	
	long countByUser_uid(Long user_id);
}
