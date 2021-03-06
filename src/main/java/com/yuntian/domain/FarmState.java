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

package com.yuntian.domain;
/**
 * 用户农田状态
 * @author jiahh 2016年8月11日
 *
 */
public enum FarmState {
	WAIT, //待开通
	SEED, //种子期
	BUD, // 萌芽期
	GROW, // 生长期
	FRUIT, // 结果期
	HARVEST,//收获期 
	CLOSED//关闭
}
