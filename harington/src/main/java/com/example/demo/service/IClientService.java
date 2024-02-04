package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Client;

public interface IClientService {

	void saveClientList( List<Client> clientList);
	List<Client> retrieveAllClient();
	List<Client> retrieveClientsByName(String name);
	
}
