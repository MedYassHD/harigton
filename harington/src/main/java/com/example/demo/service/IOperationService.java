package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Account;
import com.example.demo.model.Operation;

public interface IOperationService {

	List<Operation> retrieveHistory(Account account);
}
