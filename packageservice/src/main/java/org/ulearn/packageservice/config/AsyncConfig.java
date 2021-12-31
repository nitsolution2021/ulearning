package org.ulearn.packageservice.config;

import java.util.concurrent.Executor;
//import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.ulearn.packageservice.exception.CustomException;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;

@Configuration
public class AsyncConfig extends AsyncConfigurerSupport{

	//@Override
	@Autowired
	private CustomException customException;
	//@Override
	@Bean("asyncExecutor")
	public Executor getAsyncExecutor() {
		// TODO Auto-generated method stub
		
		ThreadPoolTaskExecutor taskExecutor= new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(10);
		taskExecutor.setQueueCapacity(500);
		taskExecutor.setThreadNamePrefix("Async-thread prefix");
		taskExecutor.initialize();
		return taskExecutor;
		//return super.getAsyncExecutor();
	}
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		// TODO Auto-generated method stub
		//return super.getAsyncUncaughtExceptionHandler();
		return customException;
	}
}
