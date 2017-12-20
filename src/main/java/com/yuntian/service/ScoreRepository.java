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


import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.yuntian.domain.Score;
import com.yuntian.domain.SysUser;

public interface ScoreRepository extends CrudRepository<Score,Long>{

    /**通过username查找用户信息;*/
    //public SysUser findByUser(SysUser user);
	//List<Score> findByUser(SysUser user);
	
	Score findByUseridAndNameAndCreateday(Long userid,String name,long createtime);
	List<Score> findByuserid(Long userid);
	//List<Score> findByUseridAndName(Long userid,String name);

}