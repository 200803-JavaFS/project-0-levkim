package com.revature.dao;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.User;

public interface AccountDAOImpl {
	
	public List<Account> findAll();
	public List<Account> findByUser(User u);
	public List<Account> findByStatus(String status);
	public Account findById(int id);
	public boolean addAccount(Account a);
	public boolean updateAccount(Account a);
	
	public boolean approveAccount(Account a);
	public boolean denyAccount(Account a);
	public boolean closeAccount(int id);
	public boolean addAccountWithUser(Account a);
	
	public boolean updateFunds(double fund, int id);
	public boolean transferFunds(int original, int target, double fund);

}
