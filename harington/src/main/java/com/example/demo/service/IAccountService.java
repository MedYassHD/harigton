package com.example.demo.service;

import java.util.List;

import com.example.demo.Exception.AccountException;
import com.example.demo.model.Account;

public interface IAccountService {

	void depositMoney(Account account, long amount) throws AccountException;
	void withdrawMoney(Account account, long amount) throws AccountException;
	void insertAccount(Account account);
	List<Account> getAllAccounts() throws AccountException ;
	Account getAccountByAccountNumber(String accountNumber) throws AccountException ;

	
}
