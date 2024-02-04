package com.example.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String accountNumber;
	private long balance;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
	private Client client;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Operation> operations;

	public void withdraw(long amount) {
		if (amount > 0 && amount <= balance) {
			balance -= amount;
		} else {
			throw new IllegalArgumentException("Invalid withdrawal amount or insufficient funds");
		}
	}

	public void deposit(long amount) {
		if (amount > 0) {
			balance += amount;
		} else {
			throw new IllegalArgumentException("Invalid deposit amount");
		}
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

}
