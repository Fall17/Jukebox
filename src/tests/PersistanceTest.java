package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import model.NormalUser;
import model.SongQueue;
import model.User;
import model.Users;


// IN ORDER TO ENSURE THE TEST ARE WORKING PROPERLY DELETE THE SAVE FILE YOU WISH TO TEST

public class PersistanceTest{

	@Test 
	public void testUsersPersistance(){
		Users users = new Users();
		
		assertTrue(users.logIn("Merlin", "7777777") != null);
		assertTrue(users.logIn("Chris", "1") != null);
		users.addUser("bob", "12");
		assertTrue(users.logIn("bob", "12")!= null);
		
		users.writeToFile();
		
		Users users2 = new Users();
		assertTrue(users2.logIn("Merlin", "7777777") != null);
		assertTrue(users2.logIn("Chris", "1") != null);
		assertTrue(users2.logIn("bob", "12")!= null);
		
	}
	
	@Test
	public void testSongQueue() {
		SongQueue sq = new SongQueue();
		Users users = new Users();
		User user = users.logIn("Merlin", "7777777");
		sq.playSong("LopingSting", user);
		sq.playSong("SwingCheese", user);
		
		assertTrue(sq.nextSong().getName().equals("LopingSting"));
		sq.writeToFile();
		
		SongQueue sq2 = new SongQueue();
		assertTrue(sq2.nextSong().getName().equals("SwingCheese"));
		sq2.writeToFile();
	}
}
