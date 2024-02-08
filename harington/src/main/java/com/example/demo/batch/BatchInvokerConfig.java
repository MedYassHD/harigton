package com.example.demo.batch;

import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.service.BatchServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;

//@Configuration
public class BatchInvokerConfig {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job myJob; 

    @Bean
    public BatchServiceImpl batchInvoker() {
        return new BatchServiceImpl(jobLauncher, myJob);
    }
}
