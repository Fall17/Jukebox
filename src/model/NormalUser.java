package model;

import java.io.Serializable;

/**
 * Normal user with no extra privileges
 * 
 * @author Derian Davila Acuna
 *
 */
public class NormalUser extends User{
	public NormalUser(String userName, String password) {
		super(userName, password);
	}
}