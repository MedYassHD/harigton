package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dao.OperationDAO;
import com.example.demo.model.Account;
import com.example.demo.model.Operation;

@Service
public class OperationServiceImpl implements IOperationService {
	
	private OperationDAO operationDAO;
	
	public OperationServiceImpl( OperationDAO dao ) {
		this.operationDAO = dao;
	}

	@Override
	public List<Operation> retrieveHistory(Account account) {
		return operationDAO.checkHistory(account);
	}

	@Override
	public void persisteOperation(Operation operation) {
		operationDAO.addOperation(operation);
	}

}
