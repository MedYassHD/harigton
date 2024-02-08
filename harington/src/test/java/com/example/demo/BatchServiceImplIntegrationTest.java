package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SpringBatchTest
public class BatchServiceImplIntegrationTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Test
	void testInvokeBatch_Success() throws Exception {
		// Call the method to be tested
		JobExecution jobExecution = jobLauncherTestUtils.launchJob();

		// Verify the job execution status
		assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

		// You can perform further assertions on jobExecution if needed
		assertTrue(jobExecution.getAllFailureExceptions().isEmpty());
	}
}
