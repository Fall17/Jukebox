package model;

import java.io.Serializable;

//A normal user with no extra privliges
public class NormalUser extends User{
	public NormalUser(String userName, String password) {
		super(userName, password);
	}
}