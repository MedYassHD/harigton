package com.example.demo.service;

import com.example.demo.Exception.AccountException;
import com.example.demo.dao.AccountDAO;
import com.example.demo.model.Account;

public class AccountServiceImpl implements IAccountService {

	private AccountDAO accountDAO;
	
	public AccountServiceImpl() {
		this.accountDAO = new AccountDAO();
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

}
