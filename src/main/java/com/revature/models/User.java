package com.revature.models;

public class User {
	
	private int userId;
	private String userType;
	private static String user;
	private static String pass;
	private String fname;
	private String lname;

	public User() {
		super();
	}
	
	public User(String user, String pass) {
		super();
		setUser(user);
		setPass(pass);
	}
	
	public User(String user, String pass, int userId) {
		this(user, pass);
		setUserId(userId);
	}
	
	public User(String user, String pass, int userId, String fname, String lname) {
		this(user, pass, userId);
		setFname(fname);
		setLname(lname);
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		if (this.userId < 0) {}
		else { this.userId = userId; }
	}

	public static String getUser() {
		return user;
	}

	public static void setUser(String user) {
		if (user == "") {}
		else { User.user = user; }
	}

	public static String getPass() {
		return pass;
	}

	public static void setPass(String pass) {
		if (User.pass == "") {}
		else { User.pass = pass; }
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		if (this.fname == "") {}
		else { this.fname = fname; }
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		if (this.lname == "") {}
		else { this.lname = lname; }
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		if (this.userType == "") {}
		else { this.userType = userType; }
	}

}
