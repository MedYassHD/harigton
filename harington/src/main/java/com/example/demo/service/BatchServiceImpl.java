package com.example.demo.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

@Service
public class BatchServiceImpl implements IBatchService {

	private JobLauncher jobLauncher;
	private Job myJob;

	public BatchServiceImpl(JobLauncher jobLauncher, Job myJob) {
		this.jobLauncher = jobLauncher;
		this.myJob = myJob;
	}

	@Override
	public void invokeBatch(String filePath) {

		try {
			jobLauncher.run(myJob, new JobParameters());
			System.out.println("Batch job executed successfully!");
		} catch (Exception e) {
			System.err.println("Error executing batch job: " + e.getMessage());
		}
	}

}
