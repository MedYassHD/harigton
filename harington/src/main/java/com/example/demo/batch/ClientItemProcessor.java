package com.example.demo.batch;

import org.springframework.batch.item.ItemProcessor;

import com.example.demo.model.Client;

public class ClientItemProcessor implements ItemProcessor<Client, Client> {
	@Override
	public Client process(Client client) {
		return client;
	}
}