package tests;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import model.AdminUser;
import model.NormalUser;
import model.User;
import model.Users;

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
}
