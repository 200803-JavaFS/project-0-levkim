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
	public Account a;
	
	public void prompt() {
		System.out.println("Hello! Are you a returning customer, or a new user?");
		System.out.println("[L] Returning customer ");
		System.out.println("[N] New user");
		System.out.println("[X] Exit");
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
		case "X":
			logout();
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
		System.out.println("[A] See all accounts");
		System.out.println("[C] Create new account");
		System.out.println("[D] Deposit");
		System.out.println("[W] Withdraw");
		System.out.println("[T] Transfer");
		System.out.println("[X] Exit");
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
					System.out.println("please wait for an admin to approve your account to being using your new account.");
				} else {
					log.warn("somethign went wrong in the creation of account.");
				}
			} catch (Exception e) {
				log.error("error occurred during account creation!");
				e.printStackTrace();
			}
			break;
		case "D":
			try {
				System.out.println("Select the account id you want to deposit into.");
				int acctId = scan.nextInt();
				scan.nextLine();
				
				if (acctOps.findById(acctId).getStatus().equals("pending")) {
					System.out.println("uh oh! your account is still pending.");
					System.out.println("you cannot make deposits until an admin approves your account.");
					System.out.println("sending you back to the main menu...");
					custPrompt();
					break;
				}
				
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
					log.error("something went wrong with the deposit!");
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
				
				if (acctOps.findById(acctId).getStatus().equals("pending")) {
					System.out.println("uh oh! your account is still pending.");
					System.out.println("you cannot make withdrawals until an admin approves your account.");
					System.out.println("sending you back to the main menu...");
					custPrompt();
					break;
				}
				
				System.out.println("Input the amount you want to withdraw.");
				double take = scan.nextDouble();
				scan.nextLine();
				
				log.info("withdrawing from account " + acctId + " with " + take);
				a = acctOps.findById(acctId);
				double oldbal = a.getBalance();
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
				
				if (acctOps.findById(give).getStatus().equals("pending")) {
					System.out.println("uh oh! your account is still pending.");
					System.out.println("you cannot make transfers until an admin approves your account.");
					System.out.println("sending you back to the main menu...");
					custPrompt();
					break;
				}
				
				System.out.println("Select the account id you want to transfer TO.");
				int recieve = scan.nextInt();
				scan.nextLine();
				System.out.println("Input the amount you want to transfer.");
				double fund = scan.nextDouble();
				scan.nextLine();
				
				log.info("transferring " + fund +" from account " + give + " to account " + recieve);
				Account a1 = acctOps.findById(give);
				Account a2 = acctOps.findById(recieve);
				double oldbal1 = a1.getBalance();
				double newbal1 = oldbal1 - fund;
				double oldbal2 = a2.getBalance();
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
		case "X":
			logout();
			break;
		default:
			System.out.println("Whoops! Wrong command. Please try again!");
			break;
		}
	}
	
	// admin stuff

	private void admin() {
		System.out.println("What would you like to do today?");
		System.out.println("[A] See all accounts");
		System.out.println("[U] See all users");
		System.out.println("[O] Find one account");
		System.out.println("[S] Find one user ");
		System.out.println("[X] Exit ");
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
			List<User> users = userOps.findAll();
			for (User us : users) {
				System.out.println(us);
			}
			admin();
			break;
		// this is all for one account on admin choice
		case "O":
			try {
				System.out.println("Input the id of the account you would like to view.");
				int acctId = scan.nextInt();
				scan.nextLine();
				if (acctOps.findById(acctId) != null) {
					log.info("account found!");
					a = acctOps.findById(acctId);
					System.out.println(a);
					
					System.out.println("what would you like to do with this account?");
					if (a.getStatus().equals("pending")) {
						System.out.println("[A] Approve account");
						System.out.println("[D] Deny account");
					} 
					System.out.println("[U] Update balance");
					System.out.println("[T] Transfer balance");
					System.out.println("[C] Close account");
					String achoice = scan.nextLine();
					achoice = achoice.toUpperCase();
					
					switch (achoice) {
					case "A":
						if (a.getStatus().equals("pending")) {
							acctOps.approveAccount(a);
							if (acctOps.approveAccount(a)) {
								System.out.println("Account has been approved!");
							}
						} else {
							System.out.println("This account is no longer pending! Please pick another option");
						}
						admin();
						break;
					case "D":
						if (a.getStatus().equals("pending")) {
							acctOps.denyAccount(a);
							if (acctOps.denyAccount(a)) {
								System.out.println("Account has been denied.");
							}
						} else {
							System.out.println("This account is no longer pending! Please pick another option");
						}
						admin();
						break;
					case "U":
						System.out.println("Would you like to deposit [D] or withdraw [W]?");
						String oachoice = scan.nextLine(); 
						oachoice = oachoice.toUpperCase();
						
						if (oachoice.equals("D")) {
							try {
								System.out.println("Input the amount you want to deposit.");
								double deposit = scan.nextDouble();
								scan.nextLine();
								
								log.info("depositing into account " + a + " with " + deposit);
								double oldbal = a.getBalance();
								double newbal = oldbal + deposit;
								
								if (acctOps.updateFunds(newbal, acctId)) {
									log.info("deposit successful!");
									System.out.println("successfully deposited " + deposit + " into account " + a + ".");
									System.out.println("Your new balance is " + newbal);
								} else {
									log.error("something went wrong with the deposit!");
								}
							} catch (Exception e) {
								log.warn("failed to deposit");
								e.printStackTrace();
							}
						} else if (oachoice.equals("W")) {
							try {
								System.out.println("Input the amount you want to withdraw.");
								double take = scan.nextDouble();
								scan.nextLine();
								
								log.info("withdrawing from account " + a + " with " + take);
								a = acctOps.findById(a.getAccountId());
								double oldbal = a.getBalance();
								double newbal = oldbal - take;
								
								if (acctOps.updateFunds(newbal, a.getAccountId())) {
									log.info("deposit successful!");
									System.out.println("successfully withdrew " + take + " into account " + a + ".");
									System.out.println("Your new balance is " + newbal);
								} else {
									log.error("somethign went wrong with the withdraw!");
								}
							} catch (Exception e) {
								log.warn("failed to withdraw");
								e.printStackTrace();
							}
						}
						admin();
						break;
					case "T":
						try {
							System.out.println("Select the account id you want to transfer TO.");
							int recieve = scan.nextInt();
							scan.nextLine();
							System.out.println("Input the amount you want to transfer.");
							double fund = scan.nextDouble();
							scan.nextLine();
							
							log.info("transferring " + fund +" from account " + a + " to account " + recieve);
							Account a1 = acctOps.findById(a.getAccountId());
							Account a2 = acctOps.findById(recieve);
							double oldbal1 = a1.getBalance();
							double newbal1 = oldbal1 - fund;
							double oldbal2 = a2.getBalance();
							double newbal2 = oldbal2 + fund;
							
							if (acctOps.transferFunds(a1.getAccountId(), recieve, fund)) {
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
						admin();
						break;
					case "C":
						System.out.println("WARNING: this is irreversible. Do you still wish to continue?");
						String confirm = scan.nextLine();
						confirm = confirm.toLowerCase();
						
						if (a != null && confirm.equals("yes")) {
							acctOps.closeAccount(a.getAccountId());
							if (acctOps.closeAccount(a.getAccountId())) {
								System.out.println("account successfully closed.");
							} else {
								log.warn("something went wrong with closing the account.");
							}
						} else {
							admin();
						}
						break;
					default:
						System.out.println("Whoops! Wrong command. Please try again!");
						admin();
						break;
					}
				} else {
					log.warn("account does not exist!");
					System.out.println("Check your spelling and try again.");
					admin();
					break;
				}
			} catch (Exception e) {
				log.error("something went wrong in admin account management");
				e.printStackTrace();
			}
			admin();
			break;
		// this is all for one user on admin menu
		case "S":
			try {
				System.out.println("Input the id of the user you would like to view.");
				int userId = scan.nextInt();
				scan.nextLine();
				if (userOps.findById(userId) != null) {
					log.info("user found!");
					u = userOps.findById(userId);
					System.out.println(u);
					
					// this menu should affect the user specified from above
					
					System.out.println("what would you like to do with this user?");
					System.out.println("[U] Update user");
					System.out.println("[D] Delete");
					String uchoice = scan.nextLine();
					uchoice = uchoice.toUpperCase();
					
					if (uchoice.equals("U")) {
						System.out.println("What would you like to update?");
						System.out.println("[T] User type");
						System.out.println("[F] First name");
						System.out.println("[L] Last name");
						String umchoice = scan.nextLine();
						umchoice = umchoice.toUpperCase();
						
						switch(umchoice) {
						case "T":
							System.out.println("What would you like to change the user type to?");
							System.out.println("input 'customer' to make this user a customer.");
							System.out.println("input 'employee' to make this user an employee.");
							System.out.println("input 'admin' to make this user an admin.");
							String userType = scan.nextLine();
							userType = userType.toLowerCase();
							
							u.setUserType(userType);
							u = new User(userType, u.getFname(), u.getLname());
							
							userOps.updateUser(u);
							if (userOps.updateUser(u)) {
								System.out.println("successfully updated user type!");
								System.out.println(u);
							} else {
								log.warn("something went wrong while updating user type.");
							}
							admin();
							break;
						case "F":
							System.out.println("What would you like to change the user's first name to?");
							String uFname = scan.nextLine();
							u.setFname(uFname);
							u = new User(u.getUserType(), uFname, u.getLname());
							
							userOps.updateUser(u);
							if (userOps.updateUser(u)) {
								System.out.println("successfully updated user's first name!");
								System.out.println(u);
							} else {
								log.warn("something went wrong while updating user's first name.");
							}
							admin();
							break;
						case "L":
							System.out.println("What would you like to change the user's last name to?");
							String uLname = scan.nextLine();
							u.setLname(uLname);
							u = new User(u.getUserType(), u.getFname(), uLname);
							
							userOps.updateUser(u);
							if (userOps.updateUser(u)) {
								System.out.println("successfully updated user's first name!");
								System.out.println(u);
							} else {
								log.warn("something went wrong while updating user's last name.");
							}
							admin();
							break;
						default:
							System.out.println("Whoops! Wrong command. Please try again!");
							admin();
							break;
						}
					} else if (uchoice.equals("D")) {
						System.out.println("WARNING: this is irreversible. Do you still wish to continue?");
						String confirm = scan.nextLine();
						confirm = confirm.toLowerCase();
						
						if (a != null && confirm.equals("yes")) {
							userOps.deleteUser(u.getUserId());
							if (userOps.deleteUser(u.getUserId())) {
								System.out.println("user successfully deleted.");
							} else {
								log.warn("something went wrong with deleting the user.");
							}
						} else {
							System.out.println("either the account does not exist, or you decided not to delete the account.");
							System.out.println("sending you back to the admin menu...");
							admin();
							break;
						}
					} else {
						System.out.println("Whoops! Wrong command. Please try again!");
						admin();
						break;
					}
				} else {
					log.warn("user does not exist!");
					System.out.println("Check your spelling and try again.");
					admin();
					break;
				}
			} catch (Exception e) {
				log.error("something went wrong in admin user management");
				e.printStackTrace();
			}
			admin();
			break;
		case "X":
			logout();
			break;
		default:
			System.out.println("Whoops! Wrong command. Please try again!");
			admin();
			break;
		}
	}
	
	// employee stuff

	private void employee() {
		System.out.println("What would you like to do today?");
		System.out.println("[A] See all accounts");
		System.out.println("[U] See all users");
		System.out.println("[O] Find one account");
		System.out.println("[S] Find one user ");
		System.out.println("[X] Exit");
		String choice = scan.nextLine();
		
		employSwitch(choice);
	}

	private void employSwitch(String choice) {
		choice = choice.toUpperCase();
		
		switch (choice) {
		case "A":
			List<Account> accts = acctOps.findAll();
			for (Account a : accts) {
				System.out.println(a);
			}
			employee();
			break;
		case "U":
			List<User> users = userOps.findAll();
			for (User us : users) {
				System.out.println(us);
			}
			employee();
			break;
		case "O":
			try {
				System.out.println("Input the id of the account you would like to view.");
				int acctId = scan.nextInt();
				scan.nextLine();
				if (acctOps.findById(acctId) != null) {
					log.info("account found!");
					a = acctOps.findById(acctId);
					System.out.println(a);
					
					// this menu should affect the account specified from above
					
					System.out.println("what would you like to do with this account?");
					System.out.println("[V] View account");
					if (a.getStatus().equals("pending")) {
						System.out.println("[A] Approve account");
						System.out.println("[D] Deny account");
					}
					
					String eachoice = scan.nextLine();
					eachoice = eachoice.toUpperCase();
					
					switch (eachoice) {
					case "V":
						System.out.println(a);
						break;
					case "A":
						acctOps.approveAccount(a);
						if (acctOps.approveAccount(a)) {
							System.out.println("Account has been approved!");
						}
						break;
					case "D":
						acctOps.denyAccount(a);
						if (acctOps.denyAccount(a)) {
							System.out.println("Account has been denied.");
						}
						break;
					default:
						System.out.println("Whoops! Wrong command. Please try again!");
						employee();
						break;
					}
				} else {
					log.warn("account does not exist!");
					System.out.println("Check your spelling and try again.");
					employee();
					break;
				}
			} catch (Exception e) {
				log.error("somethign went wrong in the employee account management system.");
				e.printStackTrace();
			}
			employee();
			break;
		case "S":
			try {
				System.out.println("Input the id of the user you would like to view.");
				int userId = scan.nextInt();
				scan.nextLine();
				if (userOps.findById(userId) != null) {
					log.info("user found!");
					u = userOps.findById(userId);
					System.out.println(u);
				} else {
					log.warn("user does not exist!");
					System.out.println("Check your spelling and try again.");
					employee();
					break;
				}
			} catch (Exception e) {
				log.error("something went wrong in employee user management");
				e.printStackTrace();
			}
			employee();
			break;
		case "X":
			logout();
			break;
		default:
			System.out.println("Whoops! Wrong command. Please try again!");
			employee();
			break;
		}
	}

	private void logout() {
		System.out.println("Are you sure you want to log out?");
		System.out.println("[Y] / [N]");
		String confirm = scan.nextLine();
		confirm = confirm.toUpperCase();
		
		if (confirm.equals("Y")) {
			log.info("logging user out...");
			System.out.println("Thank you for visiting the Beluga Bank! Happy swimming! :)");
			scan.close();
		} else if (confirm.equals("N")) {
			System.out.println("Would you like to return to  Welcome Screen to log in as another user or register?");
			System.out.println("[Y] / [N]");
			String confirm2 = scan.nextLine();
			confirm2 = confirm2.toUpperCase();
			if (confirm.equals("Y")) {
				login();
			} else if (confirm.equals("N")) {
				logout();
			} else {
				System.out.println("Whoops! Wrong command. Please try again!");
				logout();
			}
		} else {
			System.out.println("Whoops! Wrong command. Please try again!");
			logout();
		}
	}

}
