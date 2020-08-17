package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.User;
import com.revature.utilities.ConnectUtil;

public class UserDAO implements UserDAOImpl {
	
	private static final Logger log = LogManager.getLogger(UserDAO.class);

	@Override
	public List<User> findAll() {

		try (Connection conn = ConnectUtil.getConnection()) {
			
			String sql = "SELECT * FROM users;";
			Statement stmt = conn.createStatement();
			List<User> users = new ArrayList<>();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				User u = new User(rs.getInt("user_id"),
						rs.getString("user_type"),
						rs.getString("username"),
						rs.getString("pass"),
						rs.getString("first_name"),
						rs.getString("last_name"));
				
				users.add(u);
			}
			
			return users;
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to search for all users!");
		}
		
		return null;
	}
	
	@Override
	public List<User> findByType(String type) {
		
		try (Connection conn = ConnectUtil.getConnection()) {
			
			String sql = "SELECT * FROM users WHERE user_type = " + type + ";";
			Statement stmt = conn.createStatement();
			List<User> users = new ArrayList<>();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				User u = new User(rs.getInt("user_id"),
						rs.getString("user_type"),
						rs.getString("username"),
						rs.getString("pass"),
						rs.getString("first_name"),
						rs.getString("last_name"));
				
				users.add(u);
			}
			
			return users;
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to search for users by type!");
		}
		
		return null;
	}
	
	@Override
	public User findById(int id) {
		
		try (Connection conn = ConnectUtil.getConnection()) {
			
			String sql = "SELECT * FROM users WHERE user_id = " + id + ";";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				User u = new User(rs.getInt("user_id"),
						rs.getString("user_type"),
						rs.getString("username"),
						rs.getString("pass"),
						rs.getString("first_name"),
						rs.getString("last_name"));
				
				return u;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to search for user by id!");
		}
		
		return null;
	}
	
	@Override
	public User findByUser(String user) {
		try (Connection conn = ConnectUtil.getConnection()) {
			
			String sql = "SELECT * FROM users WHERE username = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			User u = new User(user);
			
			stmt.setString(1, user);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				if (rs.getString("username").equals(user)) {
					int userId = rs.getInt("user_id");
					String userType = rs.getString("user_type");
					
					u.setUserId(userId);
					u.setUserType(userType);
					return u;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to search for user by username!");
		}
		
		return null;
	}
	
	@Override
	public boolean findByUserPass(String user, String pass) {

		try (Connection conn = ConnectUtil.getConnection()) {
			
			String sql = "SELECT * FROM users WHERE username = ? AND pass = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, user);
			stmt.setString(2, pass);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				if (rs.getString("username").equals(user) && rs.getString("pass").equals(pass)) {
					return true;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to search for user by user/pass combo!");
		}
		
		return false;
		
	}

	@Override
	public boolean addUser(User u) {
		
		try (Connection conn = ConnectUtil.getConnection()) {
			
			String sql = "INSERT INTO users (user_type, username, pass, first_name, last_name) " + 
					"VALUES ('customer', ?, ?, ?, ?);";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			int index = 0;
			stmt.setString(++index, u.getUser());
			stmt.setString(++index, u.getPass());
			stmt.setString(++index, u.getFname());
			stmt.setString(++index, u.getLname());
			
			stmt.execute();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to add user!");
		}
		
		return false;
	}

	@Override
	public boolean updateUser(User u) {
		
		try (Connection conn = ConnectUtil.getConnection()) {
			
			String sql = "UPDATE users SET user_type = ?, username = ?, pass = ?, "
					+ "first_name = ?, last_name = ? WHERE user_id = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			int index = 0;
			stmt.setString(++index, u.getUserType());
			stmt.setString(++index, u.getUser());
			stmt.setString(++index, u.getPass());
			stmt.setString(++index, u.getFname());
			stmt.setString(++index, u.getLname());
			stmt.setInt(++index, u.getUserId());
			
			stmt.execute();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to update user!");
		}
		
		return false;
	}

	@Override
	public boolean deleteUser(int userId) {
		
		try (Connection conn = ConnectUtil.getConnection()) {
			
			String sql = "DELETE * FROM users WHERE user_id = " + userId + " CASCADE;";
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to delete user!");
		}
		
		return false;
	}

}