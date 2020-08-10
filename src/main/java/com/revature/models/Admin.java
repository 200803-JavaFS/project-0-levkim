package com.revature.models;

public class Admin extends User {
	
	public Admin() {
		super();
	}
	
	public Admin(String userType) {
		super();
		setUserType("admin");
	}

}
