package com.example.demo.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;

import com.example.demo.dao.ClientDAO;
import com.example.demo.model.Client;

@Service
public class ClientServiceImpl implements IClientService {

	private ClientDAO clientDAO;
	
	public ClientServiceImpl( ClientDAO clientDAO) {
		this.clientDAO = clientDAO;
	}
	
	@Override
	public void saveClientList(List<Client> clientList) {
		for ( var c : clientList ) {
			clientDAO.insertClient(c);
		}
	}

	@Override
	public List<Client> retrieveAllClient() {
		return clientDAO.getAllClients();
	}

	@Override
	public List<Client> retrieveClientsByName(String name) {
		return clientDAO.getClientsByName(name);
	}


}
