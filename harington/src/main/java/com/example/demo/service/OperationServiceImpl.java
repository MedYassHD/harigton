package com.example.demo.service;

import java.util.List;

import com.example.demo.dao.OperationDAO;
import com.example.demo.model.Account;
import com.example.demo.model.Operation;

public class OperationServiceImpl implements IOperationService {
	
	private OperationDAO operationDAO;
	
	public OperationServiceImpl() {
		this.operationDAO = new OperationDAO();
	}

	@Override
	public List<Operation> retrieveHistory(Account account) {
		return operationDAO.checkHistory(account);
	}

}
