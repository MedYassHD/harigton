package com.example.demo;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.service.BatchServiceImpl;
import com.example.demo.service.IBatchService;

@SpringBootTest
public class BatchUnitTest {

	@Autowired
	IBatchService batchService;
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private Job myJob;
	@Autowired
	DataSource dataSource;
	
	@Test
	void test() throws SQLException {
		  try (Connection connection = dataSource.getConnection()) {
	            DatabaseMetaData metaData = connection.getMetaData();
	            try (ResultSet resultSet = metaData.getTables(null, null, null, new String[]{"TABLE"})) {
	                System.out.println("Tables in the database:");
	                while (resultSet.next()) {
	                    String tableName = resultSet.getString("TABLE_NAME");
	                    System.out.println(tableName);
	                }
	            }
	        }
	}
	
	

	@BeforeEach
	void setUp() {
		jobLauncher = mock(JobLauncher.class);
		myJob = mock(Job.class);
		batchService = new BatchServiceImpl(jobLauncher, myJob);
	}

	@Test
	void testInvokeBatch_Success() throws Exception {
		JobExecution successfulExecution = new JobExecution(1L);
		doReturn(successfulExecution).when(jobLauncher).run(eq(myJob), Mockito.any(JobParameters.class));

		batchService.invokeBatch("C:\\Users\\myhaj\\git\\Harington\\harington\\src\\main\\resources\\clients.csv");

		verify(jobLauncher).run(eq(myJob), Mockito.any(JobParameters.class));
	}

	@Test
	void testInvokeBatch_Exception() throws Exception {
		doThrow(new JobRestartException("Job restart failed")).when(jobLauncher).run(eq(myJob),
				Mockito.any(JobParameters.class));

		batchService.invokeBatch("C:\\Users\\myhaj\\git\\Harington\\harington\\src\\main\\resources\\clients.csv");

		verify(jobLauncher).run(eq(myJob), Mockito.any(JobParameters.class));
	}

}
