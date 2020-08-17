package com.revature.models;

import java.io.Serializable;

public class Account implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int accountId;
	private String type;
	private String status;
	private double balance;
	private int userId;
	
	public Account() {
		super();
	}
	
	public Account(int accountId) {
		super();
		this.accountId = accountId;
	}
	
	public Account(String type, double balance, int userId) {
		super();
		this.type = type;
		this.balance = balance;
		this.userId = userId;
	}
	
	public Account(int accountId, String type, double balance) {
		super();
		this.accountId = accountId;
		this.type = type;
		this.balance = balance;
	}
	
	public Account(int accountId, String type, double balance, String status) {
		this(accountId, type, balance);
		this.status = status;
	}
	
	public Account(int accountId, String type, double balance, String status, int userId) {
		this(accountId, type, balance, status);
		this.userId = userId;
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

	public void setBalance(double balance) {
		if (this.balance < 0) {}
		else { this.balance = balance; }
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int user) {
		this.userId = user;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountId;
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + userId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountId != other.accountId)
			return false;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (userId != other.userId) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Account #" + accountId + "\n"
				+ "   Type: " + type + "\n"
				+ "   Status: " + status + "\n"
				+ "   Current Balance: " + balance + "\n"
				+ "   Owner ID: " + userId;
	}
	
	

}
