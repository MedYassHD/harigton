package com.example.demo.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.demo.batch.ClientBatchConfig;

public class BarchServiceImpl implements IBatchService {

	@Override
	public void invokeBatch(String filePath) {

		ApplicationContext context = new AnnotationConfigApplicationContext(ClientBatchConfig.class);
		
		Job job = context.getBean(Job.class);
		JobLauncher jobLauncher = context.getBean(JobLauncher.class);

		try {

			JobExecution jobExecution = jobLauncher.run(job, new JobParameters());

			if (jobExecution.getStatus().isUnsuccessful()) {
				System.err.println("Batch Job failed with exit code: " + jobExecution.getExitStatus().getExitCode());
			} else {
				System.out.println("Batch Job completed successfully");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new BarchServiceImpl().invokeBatch("\\src\\main\\resources\\clients.csv");
	}

}
