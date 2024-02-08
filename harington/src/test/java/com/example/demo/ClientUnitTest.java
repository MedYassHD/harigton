package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.model.Client;
import com.example.demo.service.IClientService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientUnitTest {

	@MockBean
	IClientService clientService;

	@Test
	@Order(1)
	void testAddClient() throws SQLException {
		var client = new Client();
		client.setName("yassine");
		when(clientService.retrieveClientsByName(Mockito.anyString())).thenReturn(List.of(client));
		assertNotNull(clientService.retrieveClientsByName("yassine"));
	}

	@Test
	@Order(2)
	void testRetrieveAllCLients() {
		var client = new Client();
		client.setName("ala");
		when(clientService.retrieveAllClient()).thenReturn(List.of(client , new Client()));
		assertEquals(clientService.retrieveAllClient().size(), 2);
	}

}
