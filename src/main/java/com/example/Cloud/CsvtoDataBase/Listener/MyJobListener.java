package com.example.Cloud.CsvtoDataBase.Listener;

import java.util.Date;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class MyJobListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("Startd time of job:"+new Date());
		System.out.println("job Status:"+ jobExecution.getStatus());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		System.out.println("Ending time of job:"+new Date());
		System.out.println("job Status:"+ jobExecution.getStatus());
	}

}
