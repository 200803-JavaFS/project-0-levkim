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

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.utilities.ConnectUtil;

public class AccountDAO implements AccountDAOImpl {
	
	private static final Logger log = LogManager.getLogger(AccountDAO.class);

	@Override
	public List<Account> findAll() {
		
		try (Connection conn = ConnectUtil.getConnection()) {
			
			String sql = "SELECT * FROM accounts;";
			Statement stmt = conn.createStatement();
			List<Account> accts = new ArrayList<>();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				Account a = new Account(rs.getInt("account_id"),
						rs.getString("account_type"),
						rs.getDouble("balance"),
						rs.getString("status"),
						rs.getInt("user_id_fk"));
				
				accts.add(a);
			}
			
			return accts;
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to search all accounts!");
		}
		
		return null;
	}
	
	@Override
	public List<Account> findByUser(User u) {
		
		try (Connection conn = ConnectUtil.getConnection()) {
			
			String sql = "SELECT * FROM accounts WHERE user_id_fk =" + u.getUserId() + ";";
			Statement stmt = conn.createStatement();
			List<Account> accts = new ArrayList<>(); 
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				Account a = new Account(rs.getInt("account_id"),
						rs.getString("account_type"),
						rs.getDouble("balance"),
						rs.getString("status"),
						rs.getInt("user_id_fk"));
				
				accts.add(a);
			}
			
			return accts;
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to search account by user id!");
		}
		
		return null;
	
	}
	

	@Override
	public List<Account> findByStatus(String status) {
		
		try (Connection conn = ConnectUtil.getConnection()) {
			
			String sql = "SELECT * FROM accounts WHERE status =" + status + ";";
			Statement stmt = conn.createStatement();
			List<Account> accts = new ArrayList<>(); 
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				Account a = new Account(rs.getInt("account_id"),
						rs.getString("account_type"),
						rs.getDouble("balance"),
						rs.getString("status"),
						rs.getInt("user_id_fk"));
				
				accts.add(a);
			}
			
			return accts;
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to search account by user id!");
		}
		
		return null;
	}

	@Override
	public Account findById(int id) {
		
		try (Connection conn = ConnectUtil.getConnection()) {
			
			String sql = "SELECT * FROM accounts WHERE account_id =" + id + ";";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				Account a = new Account(rs.getInt("account_id"),
						rs.getString("account_type"),
						rs.getDouble("balance"),
						rs.getString("status"),
						rs.getInt("user_id_fk"));
				return a;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to search account by id!");
		}
		
		return null;
	}

	@Override
	public boolean addAccount(Account a) {
		try (Connection conn = ConnectUtil.getConnection()) {
			
			String sql = "BEGIN;"
					+ "INSERT INTO accounts (account_type, balance, status, user_id_fk) "
					+ "VALUES (?, ?, 'pending', ?);"
					+ "COMMIT;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			int index = 0;
			stmt.setString(++index, a.getType());
			stmt.setDouble(++index, a.getBalance());
			stmt.setInt(++index, a.getUserId());
			
			stmt.execute();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to create account!");
		}
		return false;
	}

	@Override
	public boolean approveAccount(Account a) {

		try (Connection conn = ConnectUtil.getConnection()) {
			
			String sql = "UPDATE accounts SET status = 'open' WHERE account_id = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, a.getAccountId());
			
			stmt.execute();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to approve account!");
		}
		
		return false;
	}

	@Override
	public boolean denyAccount(Account a) {
		
		try (Connection conn = ConnectUtil.getConnection()) {
			
			String sql = "UPDATE accounts SET status = 'denied' WHERE account_id = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, a.getAccountId());
			
			stmt.execute();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to deny account!");
		}
		
		return false;
	}

	@Override
	public boolean closeAccount(int id) {
		
		try (Connection conn = ConnectUtil.getConnection()) {
			
			String sql = "UPDATE accounts SET status = 'closed' WHERE account_id = " + id + ";";
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to close account!");
		}
		
		return false;
	}

	@Override
	public boolean updateFunds(double fund, int id) {
		
		try (Connection conn = ConnectUtil.getConnection()) {
			
			String sql = "BEGIN TRANSACTION;"
					+ "UPDATE accounts SET balance = ? WHERE account_id = ?;"
					+ "COMMIT;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setDouble(1, fund);
			stmt.setInt(2, id);
			
			stmt.execute();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to update balance in account!");
		}
		
		return false;
	}

	@Override
	public boolean transferFunds(int original, int target, double fund) {
		
		try (Connection conn = ConnectUtil.getConnection()) {
			
			String sql = "BEGIN TRANSACTION;"
					+ "UPDATE accounts SET balance =- " + fund + " WHERE account_id = ?;"
					+ "UPDATE accounts SET balance =+ " + fund + " WHERE account_id = ?;"
					+ "COMMIT;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setDouble(1, original);
			stmt.setInt(2, target);
			
			stmt.execute();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to transfer funds!");
		}
		
		return false;
	}

	@Override
	public boolean updateAccount(Account a) {
		
		try (Connection conn = ConnectUtil.getConnection()) {
			
			String sql = "BEGIN;"
					+ "UPDATE accounts SET user_id_fk = ? WHERE account_id = ?;"
					+ "COMMIT;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, a.getUserId());
			stmt.setInt(2, a.getAccountId());
			
			stmt.execute();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to approve account!");
		}

		return false;
	}

}
