package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.Exception.AccountException;
import com.example.demo.model.Account;
import com.example.demo.model.Client;
import com.example.demo.model.Operation;
import com.example.demo.service.IAccountService;
import com.example.demo.service.IClientService;
import com.example.demo.service.IOperationService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTest {

	@Autowired
	private IClientService clientService;
	
	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private IOperationService operationService;
	
	private static Account account = new Account();
	
	@BeforeAll
	static void init() {
		account.setAccountNumber("acc12657");
		account.setBalance(200L);
		account.setId(101);
	}

	@Test
	@Order(1)
	void testAddClient() throws SQLException {
		var client = new Client();
		client.setName("yassine");
		clientService.saveClientList(List.of(client));
		assertNotNull(clientService.retrieveClientsByName("yassine"));
	}

	@Test
	@Order(5)
	void holeScenario() {
		var client = new Client();
		client.setName("yassine");
		client.setId(3);
		clientService.saveClientList(List.of(client));
		
		client.setAccount(account);
		
		var operation = new Operation();
		operation.setAmount(300L);
		operation.setDescription("paying bills");
		operation.setId(2);
		operation.setTimestamp(new Date());
		operation.setAccount(account);
		operationService.persisteOperation(operation);
		assertTrue(operationService.retrieveHistory(account).size() == 1);
	}
	
	@Test
	@Order(2)
	void testRetrieveAllCLients() {
		var client = new Client();
		client.setName("ala");
		clientService.saveClientList(List.of(client));
		assertTrue(clientService.retrieveAllClient().size() >= 3);
	}
	
	
	@Test
	@Order(1)
	void testAddAccount() throws AccountException {
		var client = new Client();
		client.setName("mohamed");
		client.setId(20);
		clientService.saveClientList(List.of(client));
		var cl = clientService.retrieveClientsByName("mohamed").get(0);
		account.setClient(cl);
		accountService.insertAccount(account);
		account = accountService.getAccountByAccountNumber("acc12657");
		assertNotNull(accountService.getAccountByAccountNumber("acc12657"));
	}
	
	@Test
	@Order(2)
	void testDepositMoney() throws AccountException {
		accountService.depositMoney(account, 50L);
		assertEquals(accountService.getAccountByAccountNumber("acc12657").getBalance(), 250L);
	}
	
	@Test
	@Order(2)
	void testAccountNotFOund() throws AccountException {
        assertThrows(AccountException.class, () -> {
            accountService.depositMoney(new Account(), 0l);
        });
	}
	

	@Test
	@Order(3)
	void testWithdraw() throws AccountException {
		accountService.withdrawMoney(account, 30L);
		assertEquals(accountService.getAccountByAccountNumber("acc12657").getBalance(), 220L);
	}
	
	@Test
	@Order(4)
	void testGetAllAccounts() throws AccountException {
		assertTrue(accountService.getAllAccounts().size() == 1);
	}
}
