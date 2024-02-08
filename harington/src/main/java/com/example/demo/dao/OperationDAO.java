package com.example.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Account;
import com.example.demo.model.Operation;

@Repository
public class OperationDAO {
	private static final String INSERT_OPERATION_QUERY = "INSERT INTO operation (description, amount, timestamp, account_id) VALUES (?, ?, ?, ?)";
	private static final String CHECK_HISTORY_QUERY = "SELECT * FROM operation WHERE account_id = ? ORDER BY timestamp DESC";

	private final DataSource dataSource;
	
	public OperationDAO ( DataSource dataSource ) {
		this.dataSource = dataSource;
	}
	
	public void addOperation(Operation operation) {
		try (var connection = dataSource.getConnection();
				var preparedStatement = connection.prepareStatement(INSERT_OPERATION_QUERY)) {
			preparedStatement.setString(1, operation.getDescription());
			preparedStatement.setLong(2, operation.getAmount());
			preparedStatement.setTimestamp(3, new java.sql.Timestamp(operation.getTimestamp().getTime()));
			preparedStatement.setInt(4, operation.getAccount().getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error adding operation to the database", e);
		}
	}

	public List<Operation> checkHistory(Account account) {
		List<Operation> operations = new ArrayList<>();
		try (var connection = dataSource.getConnection();
				var preparedStatement = connection.prepareStatement(CHECK_HISTORY_QUERY)) {
			preparedStatement.setInt(1, account.getId());
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Operation operation = new Operation();
					operation.setId(resultSet.getInt("id"));
					operation.setDescription(resultSet.getString("description"));
					operation.setAmount(resultSet.getLong("amount"));
					operation.setTimestamp(resultSet.getTimestamp("timestamp"));
					operations.add(operation);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error checking operation history from the database", e);
		}
		return operations;
	}

}
