package com.example.demo.batch;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import com.example.demo.dao.ClientDAO;
import com.example.demo.model.Client;

public class ClientItemWriter implements ItemWriter<Client> {

	private final ClientDAO clientDAO;

	public ClientItemWriter(ClientDAO clientDAO) {
		this.clientDAO = clientDAO;
	}

	@Override
	public void write(Chunk<? extends Client> clients) throws Exception {
		for (var client : clients.getItems()) {
			clientDAO.insertClient(client);
		}

	}
}
