package com.revature.utilities;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOImpl;
import com.revature.models.Account;
import com.revature.models.User;

public class UserOps {
	
	private static UserDAOImpl dao = new UserDAO();
	private static final Logger log = LogManager.getLogger(UserOps.class);
	
	public List<User> findAll() {
		log.info("searching for all users...");
		List<User> list = dao.findAll();
		return list;
	}
	
	public List<User> findByType(String type) {
		log.info("searching for all users by type " + type);
		return dao.findByType(type);
	}
	
	public List<Account> findAccounts(Account a) {
		log.info("searching for all accounts");
		List<Account> list = dao.findAccounts(a);
		return list;
	}
	
	public User findById(int id) {
		log.info("searching for user id " + id);
		return dao.findById(id);
	}
	
	public User findByUser(String user) {
		log.info("searching for user " + user);
		return dao.findByUser(user);
	}
	
	public boolean findByUserPass(String user, String pass) {
		log.info("searching for user " + user);
		if (dao.findByUserPass(user, pass)) {
			return true;
		}
		return false;
	}
	
	public boolean addUser(User u) {
		log.info("adding user: " + u);
		if (dao.addUser(u)) {
			return true;
		}
		return false;
	}
	
	public boolean updateUser(User u) {
		log.info("updating user: " + u);
		if (dao.updateUser(u)) {
			return true;
		}
		return false;
	}
	
	public boolean deleteUser(int userId) {
		log.info("deleting user with id " + userId);
		if (dao.deleteUser(userId)) {
			return true;
		}
		return false;
	}

}
