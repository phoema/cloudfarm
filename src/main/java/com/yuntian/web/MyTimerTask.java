package com.yuntian.web;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yuntian.domain.Farm;
import com.yuntian.domain.FarmState;
import com.yuntian.service.FarmRepository;


 
/**
 * 定时器
 * @author jiahh 2016年8月23日
 *
 */
@Slf4j
@Component
public class MyTimerTask{

	@Autowired
	FarmRepository farmRepository;
	
	//农田状态更新定时器
	// 定时任务 每天凌晨1点触发
	@Scheduled(cron="0 15 9 * * ?")   
	// 定时任务 每50秒触发一次
	//@Scheduled(fixedDelay=50000)  
	public void run() {
		// TODO Auto-generated method stub
		// 生长状态更新
		// 播种期10%天 萌芽期20%天 生长期 30%结果期 40% 关闭
		Date curdate = new Date();
		log.info(String.format("触发时间:%s", curdate));
		Calendar farmCalendar = Calendar.getInstance();
		Calendar curCalendar = Calendar.getInstance();
		curCalendar.setTime(curdate);
		List<Farm> list = (List<Farm>)farmRepository.findByStateNot(FarmState.CLOSED);
		for(Farm farm :list){
			FarmState curstate = FarmState.SEED;
			// 获取状态
			FarmState state = farm.getState();
			// 获取开通农田日与当前日期之差
			farmCalendar.setTime(farm.getCreatetime());
			int x = curCalendar.get(Calendar.DAY_OF_YEAR) - farmCalendar.get(Calendar.DAY_OF_YEAR);
			// 获取周期
			long cylecday = farm.getPkage().getCycleday();
			double ret = (double)x/(double)cylecday;
			// 播种期
			if(ret <= 0.1){
				curstate = FarmState.SEED;
			}else if(ret <= 0.3){
				curstate = FarmState.BUD;
			}else if(ret <= 0.7){
				curstate = FarmState.GROW;
			}else if(ret <= 1){
				curstate = FarmState.FRUIT;
			}else{
				curstate = FarmState.HARVEST;
			}
			
			if(state != curstate){
				log.info(String.format("farmid:%s--retday:%s--oldstate:%s--cursate:%s", farm.getId(),ret,state,curstate));
				farm.setState(curstate);
				farmRepository.save(farm);
			}
		}

	}     
	//农田开通定时器
	// 定时任务 "0 15 2 7 * ?" 每月7日凌晨2点15触发 
	@Scheduled(cron="0 14 9 18 * ?")   
	// 定时任务 每50秒触发一次
	//@Scheduled(fixedDelay=50000)  
	public void openfarm() {
		// TODO Auto-generated method stub
		// 生长状态更新
		// 播种期10%天 萌芽期20%天 生长期 30%结果期 40% 关闭
		Date curdate = new Date();
		log.info(String.format("农田开通定时器触发时间:%s", curdate));
		// 查找所有待开通的农田
		List<Farm> list = (List<Farm>)farmRepository.findByState(FarmState.WAIT);
		for(Farm farm :list){
			FarmState curstate = FarmState.SEED;

			log.info(String.format("farmid:%s--农田开通", farm.getId()));
			farm.setState(curstate);
			farmRepository.save(farm);

		}

	}     

}