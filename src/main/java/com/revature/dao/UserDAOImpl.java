package com.revature.dao;

import java.util.List;

import com.revature.models.User;

public interface UserDAOImpl {
	
	public List<User> findAll();
	public List<User> findByType(String type);
	public User findById(int id);
	public User findByUser(String user);
	public boolean findByUserPass(String user, String pass);
	
	public boolean addUser(User u);
	public boolean updateUser(User u);
	public boolean deleteUser(int userId);

}
