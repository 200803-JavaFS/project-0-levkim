package com.revature.models;

public class Customer extends User {
	
	public Customer() {
		super();
	}
	
	public Customer(String userType) {
		super();
		setUserType("customer");
	}

}
