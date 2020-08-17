package com.revature.dao;

import java.util.List;

import com.revature.models.Account;

public interface AccountDAOImpl {
	
	public List<Account> findAll();
	public List<Account> findByUser(int userId);
	public List<Account> findByStatus(String status);
	public Account findById(int id);
	public boolean addAccount(Account a);
	public boolean approveAccount(Account a);
	public boolean denyAccount(Account a);
	public boolean closeAccount(int id);
	public boolean addAccountWithUser(Account a);
	
	public double updateFunds(double fund, int id);
	public double transferFunds(int original, int target, double fund);

}
