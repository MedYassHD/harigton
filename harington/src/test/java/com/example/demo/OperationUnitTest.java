package com.example.demo;

import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.model.Account;
import com.example.demo.model.Operation;
import com.example.demo.service.IOperationService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OperationUnitTest {

	@MockBean
	IOperationService operationService;
	
	@Test
	void testCheckHistory() {
		var account = new Account();
		var op1 = new Operation();
		op1.setAmount(100);
		op1.setAccount(account);
		op1.setDescription("paying taxes");
		op1.setTimestamp(new Date());
		var op2 = new Operation();
		op2.setAmount(150);
		op2.setAccount(account);
		op2.setDescription("paying schools fees");
		op2.setTimestamp(new Date());
		var opsList = List.of(op1,op2);
		when(operationService.retrieveHistory(account)).thenReturn(opsList);
		assertTrue(operationService.retrieveHistory(account).equals(opsList));
		
	}
	
}
