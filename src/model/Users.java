package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

//This class handles the list of Users 
// includes any login checks and add/removal of users

public class Users {	
	private File userFile;
	private List<User> userList;
	
	public Users() {
		userFile = new File("saveFiles/Users.txt");
		try {
			if(!userFile.createNewFile())
				readInput();
			else {
				userList = new ArrayList<User>();
				userList.add(new NormalUser("Chris", "1"));
				userList.add(new NormalUser("Devon", "22"));
				userList.add(new NormalUser("River", "333"));
				userList.add(new NormalUser("Ryan", "4444"));
				userList.add(new AdminUser("Merlin", "7777777"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
	//	writeToFile();
		return true;
	}

	//reads the user array for the user save file
	private void readInput() {
		try {

			FileInputStream input = new FileInputStream(userFile);
			ObjectInputStream objInput = new ObjectInputStream(input);
			userList = (List<User>) objInput.readObject();
			objInput.close();
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//writes the array into the save file
	public void writeToFile() {
		try {
			FileOutputStream output = new FileOutputStream(userFile);
			ObjectOutputStream objOutput = new ObjectOutputStream(output);
			objOutput.writeObject(userList);
			objOutput.close();
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
