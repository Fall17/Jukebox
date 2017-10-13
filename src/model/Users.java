package model;

import java.util.ArrayList;
import java.util.List;

//This class handles the list of Users 
// includes any login checks and add/removal of users

public class Users {	
	private List<User> userList;
	public Users() {
		userList = new ArrayList<User>();
		userList.add(new NormalUser("Chris", "1"));
		userList.add(new NormalUser("Devon", "22"));
		userList.add(new NormalUser("River", "333"));
		userList.add(new NormalUser("Ryan", "4444"));
		userList.add(new AdminUser("Merlin", "7777777"));
	}
	
	//takes in a username and password
	//if the username and password are valid this returns a the given User
	//if the username or password is not valid this will return null
	public User logIn(String username, String password) {
		for(User curUser : userList) {
			if(curUser.getUsername().equals(username)) {
				if(curUser.checkPassword(password))
					return curUser;
				else 
					return null;
			}
		}
		
		//if the username was not valid
		return null;
	}

	//removes the given user
	public void removeUser(String username) {
		for(User curUser : userList) {
			if(curUser.getUsername().equals(username)) {
				userList.remove(curUser);
				return;
			}
		}
	}
	
	//adds a user to the userList
	//returns false if there is already a user with the same username
	public boolean addUser(String username, String password) {
		for(User curUser : userList) {
			if(curUser.getUsername().equals(username)) {
				return false;
			}
		}
		
		userList.add(new NormalUser(username, password));
		return true;
	}
}
