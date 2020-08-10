package com.revature;

import java.util.Scanner;

import com.revature.models.User;

public class Application {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Welcome. Are you a returning customer, or a new user?");
		System.out.println("Returning customer [L] / New user [N]");
		String logreg = scan.nextLine();
		
		if (logreg.equalsIgnoreCase("L")) {
			System.out.println("Please login.");
			System.out.println("Input your username:");
			User.setUser(scan.next());
			System.out.println("Input your password");
			User.setPass(scan.next());
			
			System.out.println("Welcome, " + User.getUser() + "!");
		} else if (logreg.equalsIgnoreCase("N")) {
			System.out.println("Hi, new user!");
			System.out.println("Please choose a new username.");
			User.setUser(scan.next());
			System.out.println("Please choose a new password.");
			User.setPass(scan.next());
			
			System.out.println("Welcome, " + User.getUser() + "!");
		} else {
			System.out.println("You have not selected either option. Due to security purposes, this application will close. Good bye!");
			scan.close();
		}
		
		System.out.println("What would you like to do today?");
		System.out.println("Withdraw [W] / Deposit [D] / Transfer [T]");
		String acct = scan.nextLine();
		
		if (acct.equalsIgnoreCase("W")) {
			System.out.println("Please input the amount you would like to withdraw.");
			double wamt = scan.nextDouble();
			
			System.out.println("You withdrew $" + wamt + ".");
		} else if (acct.equalsIgnoreCase("D")) {
			System.out.println("Please input the amount you would like to deposit.");
			double damt = scan.nextDouble();
			
			System.out.println("You deposited $" + damt + ".");
		} else if (acct.equalsIgnoreCase("T")) {
			System.out.println("Please input the amount you would like to transfer.");
			double tamt = scan.nextDouble();
			System.out.println("Where would you like to transfer the funds to?");
			String recepient = scan.nextLine();
			
			System.out.println("You transferred $" + tamt + " to " + recepient + ".");
		} else {
			System.out.println("You have not selected either option. Due to security purposes, this application will close. Good bye!");
			scan.close();
		}
		
		scan.close();
	}
	
}
