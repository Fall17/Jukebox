package tests;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import model.AdminUser;
import model.NormalUser;
import model.User;
import model.Users;

//MOST TEST WILL NOT WORK DUE TO A CHANGE FROM A 
//BOOLEAN RETURN VALUE -> STRING RETURN VALUE IN CANPLAYSONG

public class UserTest {

	@Test
	public void testPlaySongAmount() {
		User user = new NormalUser("user" ,"22");
		assertTrue(user.canPlaySong(20));
		user.playSong(50);
		assertTrue(user.canPlaySong(20));
		user.playSong(20);
		assertTrue(user.canPlaySong(20));
		user.playSong(0);
		assertFalse(user.canPlaySong(20));
	}
	
	@Test
	public void testPlaySongTime() {
		User user = new NormalUser("user" ,"22");
		assertTrue(user.canPlaySong(1500*60-1));
		user.playSong(1500*60-1);
		assertTrue(user.canPlaySong(1));
		user.playSong(1);
		assertFalse(user.canPlaySong(20));
	}
	
	@Test
	public void testNewDay() {
		User user = new NormalUser("user" ,"22");
		LocalDate tomorrow = LocalDate.now().plusDays(1);
		user.playSong(0);
		user.playSong(0);
		user.playSong(0);
		assertFalse(user.canPlaySong(0));
		user.playSong(0, tomorrow);
		assertTrue(user.canPlaySong(0));
		user.playSong(0);
		user.playSong(0);	
		assertFalse(user.canPlaySong(0));
	}
	
	@Test
	public void testNewDayTimeLeft() {
		User user = new NormalUser("user" ,"22");
		assertTrue(user.canPlaySong(1500*60-1));
		user.playSong(1500*60-1);
		assertTrue(user.canPlaySong(1));
		user.playSong(1);
		assertFalse(user.canPlaySong(20));
	
		LocalDate tomorrow = LocalDate.now().plusDays(1);
		user.playSong(0, tomorrow);
		
		assertFalse(user.canPlaySong(1));
	}
	
	@Test
	public void testLogIn() {
		Users users = new Users();
		assertEquals(users.logIn("Chris", "11"), null);
		assertEquals(users.logIn("Chri", "1"), null);
		assertTrue(users.logIn("Chris", "1") != null);
		assertTrue(users.logIn("Merlin", "7777777") instanceof AdminUser);
		assertTrue(users.logIn("Chris", "1") instanceof NormalUser);
		
		
	}
	
	@Test
	public void testAddRemoveUsers() {
		Users users = new Users();
		assertTrue(users.logIn("Chris", "1") != null);
		assertTrue(users.logIn("Devon", "22") != null);
		assertTrue(users.logIn("River", "333") != null);
		assertTrue(users.logIn("Ryan", "4444") != null);
		assertTrue(users.logIn("Merlin", "7777777") != null);
		
		users.removeUser("Chris");
		assertFalse(users.logIn("Chris", "1") != null);
	

		assertFalse(users.logIn("Jose", "55555") != null);
		assertTrue(users.addUser("Jose", "55555"));
		assertTrue(users.logIn("Jose", "55555") != null);
		
		assertFalse(users.addUser("Ryan", "2323"));
		users.removeUser("Ryan");
		assertFalse(users.logIn("Ryan", "4444") != null);
		assertFalse(users.logIn("Ryan", "2323") != null);
		assertTrue(users.addUser("Ryan", "2323"));
		assertTrue(users.logIn("Ryan", "2323") != null);
		
	}
}
