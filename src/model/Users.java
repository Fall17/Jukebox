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
/**
 * Keeps a list of all the Users
 * 
 * @author Derian Davila Acuna
 *
 */
public class Users {	
	private File userFile;
	private List<User> userList;
	
	/**
	 * If their is a valid save file then the list of users will be loaded from that file.
	 * Otherwise this will create a new list of users and create a new savefile if their wasn't one.
	 */
	public Users() {
		userFile = new File("saveFiles/Users.txt");
		try {
			if(!userFile.createNewFile())
				readInput();
			else {
				createNewUserList();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	/** 
	 * createNewUserList creates a new userList if the save file does not contain any saved data or if the save file does not exist
	 */
	private void createNewUserList() {
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
	/**
	 * checks whether or not the username matches with any user in the list then whether or not given password is correct
	 * @return the user with the specified username
	 */
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

	/**
	 * removes the user with the given username
	 */
	public void removeUser(String username) {
		for(User curUser : userList) {
			if(curUser.getUsername().equals(username)) {
				userList.remove(curUser);
				return;
			}
		}
	}
	
	/**
	 * Adds a user to the userList with the given username and password
	 * @return returns true if a new user was added, returns false if their exist a user with the given username
	 */
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

	/**
	 * readInput reads from the Users save file. If the save file is blank then we catch the IO exception when we are reading. 
	 * If there is an IOException then we call createNewUserList 
	 */
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
			createNewUserList();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * writes the list of users into the Users.txt save file
	 */
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
