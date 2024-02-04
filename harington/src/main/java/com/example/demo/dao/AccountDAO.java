package com.example.demo.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.Exception.AccountException;
import com.example.demo.model.Account;
import com.example.demo.model.Client;

public class AccountDAO {
	private static final String INSERT_QUERY = "INSERT INTO account (account_number, balance, client_id) VALUES (?, ?, ?)";
	private static final String SELECT_ALL_QUERY = "SELECT * FROM account";
	private static final String WITHDRAW_QUERY = "UPDATE account SET balance = balance - ? WHERE id = ?";
	private static final String DEPOSIT_QUERY = "UPDATE account SET balance = balance + ? WHERE id = ?";

	public void insertAccount(Account account) {
		try (var connection = DatabaseConnection.getConnection();
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
		try (Connection connection = DatabaseConnection.getConnection();
				var preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
				var resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				var account = new Account();
				account.setId(resultSet.getInt("id"));
				account.setAccountNumber(resultSet.getString("accountNumber"));
				account.setBalance(resultSet.getLong("balance"));

				var clientId = resultSet.getInt("client_id");
				var client = getClientById(clientId);
				account.setClient(client);

				accounts.add(account);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accounts;
	}

	public void withdrawMoney(Account account, long amount) throws AccountException {
		try (var connection = DatabaseConnection.getConnection();
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
		try (var connection = DatabaseConnection.getConnection();
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

	private Client getClientById(int clientId) {

		var clientDAO = new ClientDAO();

		return clientDAO.getClientById(clientId);
	}
}
