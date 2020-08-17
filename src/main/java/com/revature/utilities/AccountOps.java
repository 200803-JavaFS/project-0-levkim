package com.revature.utilities;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.dao.AccountDAO;
import com.revature.dao.AccountDAOImpl;
import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOImpl;
import com.revature.models.Account;
import com.revature.models.User;

public class AccountOps {
	
	private static AccountDAOImpl dao = new AccountDAO();
	private static UserDAOImpl udao = new UserDAO();
	private static final Logger log = LogManager.getLogger(Account.class);
	
	public List<Account> findAll() {
		log.info("searching for all accounts...");
		List<Account> list = dao.findAll();
		return list;
	}
	
	public List<Account> findByUser(User u) {
		log.info("searching for all accounts with user id " + u + "...");
		List<Account> list = dao.findByUser(u);
		return list;
	}
	
	public List<Account> findByStatus(String status) {
		log.info("searching for all accounts with status " + status + "...");
		List<Account> list = dao.findByStatus(status);
		return list;
	}
	
	public Account findById(int id) {
		log.info("searching for account id " + id + "...");
		return dao.findById(id);
	}
	
	public boolean addAccount(Account a) {
		if (a.getUserId() != null) {
			List<User> list = udao.findAll();
			boolean b = false;
			for (User u: list) {
				if (u.equals(a.getUserId())) {
					b = true;
				}
			}
			if (b) {
				log.info("adding account: " + a);
				if (dao.addAccount(a)) {
					return true;
				}
			} else {
				log.info("adding account: " + a + " with new user: " + a.getUserId());
				if (dao.addAccountWithUser(a)) {
					return true;
				}
				return false;
			}
		}
		
		log.info("adding account: " + a);
		if (dao.addAccount(a)) {
			return true;
		}
		return false;
	}
	
	public boolean approveAccount(Account a) {
		log.info("approving application for account: " +  a);
		if (dao.approveAccount(a)) {
			return true;
		}
		return false;
	}
	
	public boolean denyAccount(Account a) {
		log.info("denying application for account: " +  a);
		if (dao.denyAccount(a)) {
			return true;
		}
		return false;
	}
	
	public boolean closeAccount(int id) {
		log.info("closing account: " +  id);
		if (dao.closeAccount(id)) {
			return true;
		}
		return false;
	}
	
	public boolean updateFunds(double fund, int id) {
		log.info("updating balance in account id " + id);
		return dao.updateFunds(fund, id);
	}
	
	public boolean transferFunds(int original, int target, double fund) {
		log.info("transferring funds from account id " + original + " to account id " + target);
		return dao.transferFunds(original, target, fund);
	}

}
