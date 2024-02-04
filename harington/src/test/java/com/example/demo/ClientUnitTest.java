package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Client;
import com.example.demo.service.ClientServiceImpl;
import com.example.demo.service.IClientService;

@SpringBootTest
public class ClientUnitTest {

	static IClientService clientService;

	@BeforeAll
	static void init() {
		clientService = new ClientServiceImpl();
	}
	
	@Test
	void testAddClient(){
		var client = new Client();
		client.setName("yassine");
		clientService.saveClientList(List.of(client));
		assertThat(clientService.retrieveClientsByName("yassine")).isNotNull();
	}
	
}
