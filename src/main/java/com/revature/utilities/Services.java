package com.revature.utilities;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.User;

public class Services {

	private static UserOps userOps = new UserOps();
	private static AccountOps acctOps = new AccountOps();
	private static final Logger log = LogManager.getLogger(Services.class);
	private static final Scanner scan = new Scanner(System.in);
	
	public void prompt() {
		System.out.println("Hello! Are you a returning customer, or a new user?");
		System.out.println("Returning customer [L] / New user [N] / Exit [E]");
		String choice = scan.nextLine();
		choiceSwitch(choice);
	}
	
	private void choiceSwitch(String choice) {
		choice = choice.toUpperCase();
		
		switch (choice) {
		case "L": 
			login();
			break;
		case "N":
			register();
			break;
		case "E":
			System.out.println("Thank you for visiting the Beluga Bank! Happy swimming! :)");
			scan.close();
			break;
		default:
			System.out.println("Whoops! Wrong command. Please try again!");
			prompt();
			break;
		}
	}

	public void login() {
		System.out.println("Please login.");
		System.out.println("Input your username:");
		String user = scan.nextLine();
		System.out.println("Input your password");
		String pass = scan.nextLine();
		
		User u = userOps.findByUser(user);
		
		log.info("Logging user in...");
		if (userOps.findByUserPass(user, pass)) {
			System.out.println("Welcome back, " + user + "!");
			if (u.getUserType().equals("admin")) {
				admin();
			} else if (u.getUserType().equals("employee")) {
				employee();
			}
		} else {
			System.out.println("Incorrect user/pass combo. Please try again.");
			login();
		}
		
		
	}

	public void register() {
		System.out.println("Hi, new user!");
		System.out.println("Please choose a new username.");
		String user = scan.nextLine();
		System.out.println("Please choose a new password.");
		String pass = scan.nextLine();
		System.out.println("Please input your first name.");
		String fname = scan.nextLine();
		System.out.println("Please input your last name.");
		String lname = scan.nextLine();

		log.info("creating new user...");
		User u = new User(user, pass, fname, lname);
		if (userOps.findByUser(user) == null) {
			u.setUserType("customer");
			userOps.addUser(u);
		} else {
			System.out.println("Someone has already used that username. Please try a different one.");
			register();
		}
		
		System.out.println("Welcome, " + user + "!");
	}
	
	private void admin() {
		System.out.println("What would you like to do today?");
		System.out.println("See all accounts [A]");
		System.out.println("See all users [U]");
		System.out.println("");
		
		
	}
	
	private void employee() {
		System.out.println("What would you like to do today?");
		System.out.println("View  [A] / Manage all users [U]");
	}

}
