package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.Exception.AccountException;
import com.example.demo.model.Account;
import com.example.demo.model.Client;
import com.example.demo.service.IAccountService;
import com.example.demo.service.IClientService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountUnitTest {

	@MockBean
	private IAccountService accountService;
	
	@MockBean
	private  IClientService clientService;
	
	private static Account account = new Account();
	
	@BeforeEach
	void init() throws AccountException {
		account.setAccountNumber("acc12657");
		account.setBalance(200L);
		account.setId(101);
		when(accountService.getAccountByAccountNumber(Mockito.anyString())).thenReturn(account);
		
		doAnswer( new Answer<Void>() {
			   @Override
	            public Void answer(InvocationOnMock invocation) throws Throwable {
	                Object[] args = invocation.getArguments(); 
	                long x = (long) args[1];
	                account.setBalance(account.getBalance() + x);
					return null;
	            }
		} ).when(accountService).depositMoney(Mockito.any(), Mockito.anyLong());
		
		doAnswer( new Answer<Void>() {
			   @Override
	            public Void answer(InvocationOnMock invocation) throws Throwable {
	                Object[] args = invocation.getArguments(); 
	                long x = (long) args[1];
	                account.setBalance(account.getBalance() - x);
					return null;
	            }
		} ).when(accountService).withdrawMoney(Mockito.any(), Mockito.anyLong());
		
	}

	@Test
	@Order(1)
	void testAddAccount() throws AccountException {
		var client = new Client();
		client.setName("mohamed");
		client.setId(20);
		clientService.saveClientList(List.of(client));
		when(clientService.retrieveClientsByName(Mockito.anyString())).thenReturn(List.of(client));
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
		doThrow( AccountException.class ).when(accountService).depositMoney(Mockito.any(), Mockito.anyLong());

        assertThrows(AccountException.class, () -> {
            accountService.depositMoney(new Account(), 0l);
        });
	}
	

	@Test
	@Order(3)
	void testWithdraw() throws AccountException {
		accountService.withdrawMoney(account, 30L);
		assertEquals(accountService.getAccountByAccountNumber("acc12657").getBalance(), 170L);
	}
	
}
