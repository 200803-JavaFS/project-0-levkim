package com.revature.models;

public class Employee extends User {
	
	public Employee() {
		super();
	}
	
	public Employee(String userType) {
		super();
		setUserType("employee");
	}

}
