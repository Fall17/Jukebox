package model;

/**
 * Differentiates between a normal user and a admin
 * Has the same basic song limitations of a normal user but can add and remove accounts
 * @author Derian Davila Acuna
 *
 */
public class AdminUser extends User{
	public AdminUser(String userName, String password) {
		super(userName, password);
	}
	
}

