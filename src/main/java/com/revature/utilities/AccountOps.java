package com.revature.utilities;

import java.util.ArrayList;

import com.revature.models.Account;

public class AccountOps {
	
	ArrayList<Account> accountlist = new ArrayList<>();
	private boolean addAcc;
	
	public void addAccount() {
		// if admin input says YES, then create new account
		if (addAcc == true) {
			
		}
		
		// if admin input says NO, then terminate account creation and go back to main menu/terminate app
	}
	
	public void cancelAccount() {
		
	}
	
	public void transferFunds() {
		// check if accountid matches user input
		// if true, then subtract funds from this account
		// then add funds to target account
	}
	
	public static void withdrawFunds() {
		// subtract funds here
	}
	
	public static void addFunds() {
		
	}

}
