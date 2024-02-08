package com.example.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.example.demo.Exception.AccountException;
import com.example.demo.model.Account;

@Repository
public class AccountDAO {
	private static final String INSERT_QUERY = "INSERT INTO account (account_number, balance, client_id) VALUES (?, ?, ?)";
	private static final String SELECT_ALL_QUERY = "SELECT * FROM account";
	private static final String WITHDRAW_QUERY = "UPDATE account SET balance = balance - ? WHERE id = ?";
	private static final String DEPOSIT_QUERY = "UPDATE account SET balance = balance + ? WHERE id = ?";

	private static final String SELECT_BY_ACCOUNT_NUMBER_QUERY = "SELECT * FROM account WHERE account_number = ?";

	private final DataSource dataSource;

	public AccountDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Account getAccountByAccountNumber(String accountNumber) {
		Account account = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ACCOUNT_NUMBER_QUERY)) {
			preparedStatement.setString(1, accountNumber);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					account = new Account();
					account.setId(resultSet.getInt("id"));
					account.setAccountNumber(resultSet.getString("account_number"));
					account.setBalance(resultSet.getLong("balance"));

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return account;
	}

	public void insertAccount(Account account) {
		try (var connection = dataSource.getConnection();
				var preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
			preparedStatement.setString(1, account.getAccountNumber());
			preparedStatement.setLong(2, account.getBalance());
			preparedStatement.setLong(3, account.getClient().getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Account> getAllAccounts() {
		List<Account> accounts = new ArrayList<>();
		try (Connection connection = dataSource.getConnection();
				var preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
				var resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				var account = new Account();
				account.setId(resultSet.getInt("id"));
				account.setAccountNumber(resultSet.getString("account_number"));
				account.setBalance(resultSet.getLong("balance"));

				accounts.add(account);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accounts;
	}

	public void withdrawMoney(Account account, long amount) throws AccountException {
		try (var connection = dataSource.getConnection();
				var preparedStatement = connection.prepareStatement(WITHDRAW_QUERY)) {
			preparedStatement.setLong(1, amount);
			preparedStatement.setInt(2, account.getId());
			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected == 0) {
				throw new AccountException("Withdrawal failed. Account not found or insufficient funds.");
			}
			account.setBalance(account.getBalance() - amount);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new AccountException("Error processing withdrawal", e);
		}
	}

	public void depositMoney(Account account, long amount) throws AccountException {
		try (var connection = dataSource.getConnection();
				var preparedStatement = connection.prepareStatement(DEPOSIT_QUERY)) {
			preparedStatement.setLong(1, amount);
			preparedStatement.setInt(2, account.getId());
			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected == 0) {
				throw new AccountException("Deposit failed. Account not found.");
			}
			account.setBalance(account.getBalance() + amount);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new AccountException("Error processing deposit", e);
		}
	}
}
