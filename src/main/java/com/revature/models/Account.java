package com.revature.models;

public class Account {
	
	private int accountId;
	private int userId;
	private String type;
	private double balance;
	
	public Account() {
		super();
	}
	
	public Account(int accountId, String type, int balance) {
		super();
		setAccountId(accountId);
		setType(type);
		setBalance(balance);
	}
	
	public Account(int accountId, String type, int balance, int userId) {
		this(accountId, type, balance);
		setUserId(userId);
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		if (this.accountId < 0) {}
		else { this.accountId = accountId; }
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if (type == "") {}
		else { this.type = type; }
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		if (this.balance < 0) {}
		else { this.balance = balance; }
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		if (this.userId < 0) {}
		else { this.userId = userId; }
	}

}
