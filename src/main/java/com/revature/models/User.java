package com.revature.models;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int userId;
	private String userType;
	private String user;
	private String pass;
	private String fname;
	private String lname;
	private List<Account> accounts;

	public User() {
		super();
	}
	
	public User(int userId) {
		super();
		this.userId = userId;
	}
	
	public User(String user) {
		super();
		this.user = user;
	}
	
	public User(String user, String pass) {
		super();
		this.user = user;
		this.pass = pass;
	}
	
	public User(String user, String pass, String fname, String lname) {
		this(user, pass);
		this.fname = fname;
		this.lname = lname;
	}
	
	public User (String userType, String user, String pass) {
		super();
		this.userType = userType;
		this.user = user;
		this.pass = pass;
	}
	
	public User(int userId, String userType, String user, String pass) {
		super();
		this.userId = userId;
		this.userType = userType;
		this.user = user;
		this.pass = pass;
	}

	public User(int userId, String userType, String user, String pass, String fname, String lname) {
		this(userId, userType, user, pass);
		this.fname = fname;
		this.lname = lname;
	}

	public User(String userType, String user, String pass, String fname, String lname) {
		this(userType, user, pass);
		this.fname = fname;
		this.lname = lname;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fname == null) ? 0 : fname.hashCode());
		result = prime * result + ((lname == null) ? 0 : lname.hashCode());
		result = prime * result + ((pass == null) ? 0 : pass.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + userId;
		result = prime * result + ((userType == null) ? 0 : userType.hashCode());
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
		User other = (User) obj;
		if (fname == null) {
			if (other.fname != null)
				return false;
		} else if (!fname.equals(other.fname))
			return false;
		if (lname == null) {
			if (other.lname != null)
				return false;
		} else if (!lname.equals(other.lname))
			return false;
		if (pass == null) {
			if (other.pass != null)
				return false;
		} else if (!pass.equals(other.pass))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (userId != other.userId)
			return false;
		if (userType == null) {
			if (other.userType != null)
				return false;
		} else if (!userType.equals(other.userType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User #" + userId + "\n"
					+ "   Type: " + userType + "\n"
					+ "   Username: " + user + "\n"
					+ "   First Name: " + fname + "\n"
					+ "   Last Name: " + lname;
	}

	

}
