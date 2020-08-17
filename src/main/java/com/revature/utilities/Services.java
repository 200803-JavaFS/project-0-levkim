package com.revature.utilities;

import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.Account;
import com.revature.models.User;

public class Services {

	private static UserOps userOps = new UserOps();
	private static AccountOps acctOps = new AccountOps();
	private static final Logger log = LogManager.getLogger(Services.class);
	private static final Scanner scan = new Scanner(System.in);
	public User u;
	
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
		
		u = userOps.findByUser(user);
		
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
		
		custPrompt();
		
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
		u = new User(user, pass, fname, lname);
		if (userOps.findByUser(user) == null) {
			u.setUserType("customer");
			userOps.addUser(u);
		} else {
			System.out.println("Someone has already used that username. Please try a different one.");
			register();
		}
		
		System.out.println("Welcome, " + user + "!");
		
		custPrompt();
	}
	
	public void custPrompt() {
		System.out.println("What would you like to do today?");
		System.out.println("See all accounts [A]");
		System.out.println("Create new account [C]");
		System.out.println("Depoist [D]");
		System.out.println("Withdraw [W]");
		System.out.println("Transfer [T]");
		System.out.println("Exit [E]");
		String choice = scan.nextLine();
		
		custSwitch(choice);
	}
	
	public void custSwitch(String choice) {
		choice = choice.toUpperCase();
		
		switch (choice) {
		case "A":
			List<Account> accts = acctOps.findByUser(u);
			for (Account a : accts) {
				System.out.println(a);
			}
			custPrompt();
			break;
		case "C":
			try {
				System.out.println("Would you like a 'checking' account, or a 'savings' account?");
				String type = scan.nextLine();
				System.out.println("How much would you like to deposit in this account?");
				double balance = scan.nextDouble();
				scan.nextLine(); 
				Account a = new Account(type, balance, u);
				
				log.info("creating account...");
				
				if (acctOps.addAccount(a)) {
					System.out.println("Successfully created new account!");
				} else {
					log.error("somethign went wrong in the creation of account.");
				}
			} catch (Exception e) {
				log.error("error on balance during account creation!");
				e.printStackTrace();
			}
			break;
		case "D":
			try {
				System.out.println("Select the account id you want to deposit into.");
				int acctId = scan.nextInt();
				scan.nextLine();
				System.out.println("Input the amount you want to deposit.");
				double deposit = scan.nextDouble();
				scan.nextLine();
				
				log.info("depositing into account " + acctId + " with " + deposit);
				double oldbal = acctOps.findById(acctId).getBalance();
				double newbal = oldbal + deposit;
				
				if (acctOps.updateFunds(newbal, acctId)) {
					log.info("deposit successful!");
					System.out.println("successfully deposited " + deposit + " into account " + acctId + ".");
					System.out.println("Your new balance is " + newbal);
				} else {
					log.error("somethign went wrong with the deposit!");
				}
			} catch (Exception e) {
				log.warn("failed to deposit");
				e.printStackTrace();
			}
			
			break;
		case "W":
			try {
				System.out.println("Select the account id you want to withdraw from.");
				int acctId = scan.nextInt();
				scan.nextLine();
				System.out.println("Input the amount you want to withdraw.");
				double take = scan.nextDouble();
				scan.nextLine();
				
				log.info("depositing into account " + acctId + " with " + take);
				double oldbal = acctOps.findById(acctId).getBalance();
				double newbal = oldbal - take;
				
				if (acctOps.updateFunds(newbal, acctId)) {
					log.info("deposit successful!");
					System.out.println("successfully withdrew " + take + " into account " + acctId + ".");
					System.out.println("Your new balance is " + newbal);
				} else {
					log.error("somethign went wrong with the withdraw!");
				}
			} catch (Exception e) {
				log.warn("failed to withdraw");
				e.printStackTrace();
			}
			break;
		case "T":
			try {
				System.out.println("Select the account id you want to transfer FROM.");
				int give = scan.nextInt();
				scan.nextLine();
				System.out.println("Select the account id you want to transfer TO.");
				int recieve = scan.nextInt();
				scan.nextLine();
				System.out.println("Input the amount you want to transfer.");
				double fund = scan.nextDouble();
				scan.nextLine();
				
				log.info("transferring " + fund +" from account " + give + " to account " + recieve);
				double oldbal1 = acctOps.findById(give).getBalance();
				double newbal1 = oldbal1 - fund;
				double oldbal2 = acctOps.findById(recieve).getBalance();
				double newbal2 = oldbal2 + fund;
				
				if (acctOps.transferFunds(give, recieve, fund)) {
					log.info("transfer successful!");
					System.out.println("successfully transferred " + fund + " into account " + recieve + ".");
					System.out.println("Your new balance is " + newbal1);
					System.out.println("New balance in account " + recieve + " is " + newbal2);
				} else {
					log.error("somethign went wrong with the transfer!");
				}
			} catch (Exception e) {
				log.warn("failed to transfer");
				e.printStackTrace();
			}
			break;
		case "E":
			System.out.println("Thank you for visiting the Beluga Bank! Happy swimming! :)");
			scan.close();
			break;
		default:
			System.out.println("Whoops! Wrong command. Please try again!");
			break;
		}
	}

	private void admin() {
		System.out.println("What would you like to do today?");
		System.out.println("See all accounts [A]");
		System.out.println("See all users [U]");
		System.out.println("Find one account [O]");
		System.out.println("Find one user [S]");
		System.out.println("Approve account [P]");
		System.out.println("Deny account [D]");
		System.out.println("Close account [C]");
		System.out.println("Exit [E]");
		String choice = scan.nextLine();
		
		adminSwitch(choice);
	}
	
	private void adminSwitch(String choice) {
		choice = choice.toUpperCase();
		
		switch (choice) {
		case "A":
			List<Account> accts = acctOps.findAll();
			for (Account a : accts) {
				System.out.println(a);
			}
			admin();
			break;
		case "U":
			// see all users
			break;
		case "O":
			System.out.println("what would you like to do with this account?");
			break;
		case "S":
			System.out.println("what would you like to do with this user?");
			break;
		case "P":
			// approve acct
			break;
		case "D":
			// deny acct
			break;
		case "C":
			// close acct
			break;
		case "E":
			System.out.println("Thank you for visiting the Beluga Bank! Happy swimming! :)");
			scan.close();
			break;
		default:
			System.out.println("Whoops! Wrong command. Please try again!");
			break;
		}
	}

	private void employee() {
		System.out.println("What would you like to do today?");
		System.out.println("See all accounts [A]");
		System.out.println("See all users [U]");
		System.out.println("Find one account [O]");
		System.out.println("Find one user [S]");
		System.out.println("Approve account [P]");
		System.out.println("Deny account [D]");
		System.out.println("Exit [E]");
		String choice = scan.nextLine();
		
		employSwitch(choice);
	}

	private void employSwitch(String choice) {
		choice = choice.toUpperCase();
		
		switch (choice) {
		case "A":
			// ask if you want to see pending accounts
			break;
		case "U":
			// see all users
			break;
		case "O":
			// find account info
			break;
		case "S":
			// find user info
			break;
		case "P":
			// find account info
			break;
		case "D":
			// find user info
			break;
		case "E":
			System.out.println("Thank you for visiting the Beluga Bank! Happy swimming! :)");
			scan.close();
			break;
		default:
			System.out.println("Whoops! Wrong command. Please try again!");
			break;
		}
	}

}
