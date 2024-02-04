package com.example.demo.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.demo.dao.ClientDAO;
import com.example.demo.dao.DatabaseConnection;
import com.example.demo.model.Client;

@Configuration
@PropertySource("classpath:application.properties")
@Import({ DatabaseConnection.class, TransactionManagerConfig.class })
public class ClientBatchConfig {

	private PlatformTransactionManager transactionManager;
	private ClientDAO clientDAO;

	@Value("${csv.file.path}")
	private String csvFilePath;

	public ClientBatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager,DataSource dataSource) {
		this.transactionManager = transactionManager;
		this.clientDAO = new ClientDAO();
	}

	@Bean
	public FlatFileItemReader<Client> reader() {
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames("name");

		DefaultLineMapper<Client> lineMapper = new DefaultLineMapper<>();
		lineMapper.setLineTokenizer(tokenizer);

		lineMapper.setFieldSetMapper(new BeanWrapperFieldSetMapper<Client>() {
			{
				setTargetType(Client.class);
			}
		});

		FlatFileItemReader<Client> reader = new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("clients.csv"));
		reader.setLineMapper(lineMapper);

		return reader;
	}

	@Bean
	public ClientItemProcessor processor() {
		return new ClientItemProcessor();
	}

	@Bean
	public ClientItemWriter writer() {
		return new ClientItemWriter(this.clientDAO);
	}

	@Bean
	public Step step(FlatFileItemReader<Client> reader, ClientItemProcessor processor, ClientItemWriter writer, JobRepository jobRepository) {
		return new StepBuilder("csv-step", jobRepository).<Client, Client>chunk(10, transactionManager).reader(reader)
				.processor(processor).writer(writer).build();
	}

	@Bean
	public Job importClientJob(Step step, JobRepository jobRepository) {
		return new JobBuilder("csv-job", jobRepository).incrementer(new RunIdIncrementer()).flow(step).end().build();
	}

}
