package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

//@Configuration
public class DatabaseConnection {
//	private static final String URL = "jdbc:h2:mem:testdb";
//	private static final String USER = "sa";
//	private static final String PASSWORD = "";
//
//	public static Connection getConnection() throws SQLException {
//		return DriverManager.getConnection(URL, USER, PASSWORD);
//	}
//
////	@Bean("dataSource")
//	public DataSource dataSource() {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName("org.h2.Driver");
//		dataSource.setUrl(URL);
//		dataSource.setUsername(USER);
//		dataSource.setPassword(PASSWORD);
//		return dataSource;
//	}
}
