package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Exception.AccountException;
import com.example.demo.dao.AccountDAO;
import com.example.demo.model.Account;

@Service
public class AccountServiceImpl implements IAccountService {

	private AccountDAO accountDAO;
	
	public AccountServiceImpl(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}
	
	@Override
	public void depositMoney(Account account, long amount) throws AccountException {
			accountDAO.depositMoney(account, amount);
	}

	@Override
	public void withdrawMoney(Account account, long amount) throws AccountException {
		accountDAO.withdrawMoney(account, amount);
	}

	@Override
	public void insertAccount(Account account) {
		accountDAO.insertAccount(account);
	}

	@Override
	public List<Account> getAllAccounts() throws AccountException {
		return accountDAO.getAllAccounts();
	}

	@Override
	public Account getAccountByAccountNumber(String accountNumber) throws AccountException {
		return accountDAO.getAccountByAccountNumber(accountNumber);
	}

}
