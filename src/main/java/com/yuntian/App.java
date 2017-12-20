package com.yuntian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * springboot快速启动项
 * 可以使用其他方式启动，通过maven可以进行控制
 * @author jiahh
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties
@EnableScheduling  //配置和使用定时器
public class App {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(App.class, args);
	}

}
