package com.example.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Account;
import com.example.demo.model.Client;

@Repository
public class ClientDAO {
	private static final String INSERT_QUERY = "INSERT INTO client (name) VALUES (?)";
	private static final String SELECT_ALL_QUERY = "SELECT * FROM client";
	private static final String SELECT_BY_ID_QUERY = "SELECT * FROM client WHERE id = ?";
	private static final String SELECT_BY_NAME_QUERY = "SELECT * FROM client WHERE name LIKE ?";

	   public void insertClient(Client client) {
	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

	            preparedStatement.setString(1, client.getName());
	            preparedStatement.executeUpdate();

	            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                Account account = client.getAccount();
	                if (account != null) {
//	                    account.setClient(client);
	                    new AccountDAO().insertAccount(account);  
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	public List<Client> getClientsByName(String name) {
		List<Client> clients = new ArrayList<>();
		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME_QUERY)) {
			preparedStatement.setString(1, "%" + name + "%");
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Client client = new Client();
					client.setId(resultSet.getInt("id"));
					client.setName(resultSet.getString("name"));
					// set other fields
					clients.add(client);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error retrieving clients by name from the database", e);
		}
		return clients;
	}

	public List<Client> getAllClients() {
		List<Client> clients = new ArrayList<>();
		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				Client client = new Client();
				client.setId(resultSet.getInt("id"));
				client.setName(resultSet.getString("name"));
				clients.add(client);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clients;
	}

	public Client getClientById(int clientId) {
		Client client = null;
		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
			preparedStatement.setInt(1, clientId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					client = new Client();
					client.setId(resultSet.getInt("id"));
					client.setName(resultSet.getString("name"));
					// set other fields
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return client;
	}
}
