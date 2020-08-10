package com.revature.utilities;

import java.util.ArrayList;

import com.revature.models.User;

public class UserOps {
	
	ArrayList<User> userlist = new ArrayList<>();
	
	private boolean addUser;

	public void addUser() {
		if (addUser == true) {
			User u = new User();
			userlist.add(u);
		}
	}

}
