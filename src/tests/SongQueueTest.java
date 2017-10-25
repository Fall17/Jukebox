package tests;
// Added to allow package tests to exist on GitHub
import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import model.NormalUser;
import model.Song;
import model.SongQueue;
import model.User;

//MOST TEST WILL NOT WORK DUE TO A CHANGE FROM
//BOOLEAN RETURN VALUE -> STRING RETURN VALUE IN CANPLAYSONG

public class SongQueueTest {


	@Test
	public void testSong() {

		Song song = new Song("songfiles/UntameableFire.mp3", "UntameableFire", 5, "Pierre Langor");
		assertEquals(song.getName(), "UntameableFire");
		assertEquals(song.getTimesPlayed(), 0);
		assertEquals(song.getPath(), "songfiles/UntameableFire.mp3");

		song.playSong();
		assertEquals(song.getTimesPlayed(), 1);
		song.playSong();
		assertEquals(song.getTimesPlayed(), 2);
	}
	
	@Test
	public void testSongNewDay() {
		Song song = new Song("songfiles/UntameableFire.mp3", "UntameableFire", 5, "Pierre Langor");
		assertEquals(song.getName(), "UntameableFire");
		assertEquals(song.getTimesPlayed(), 0);
		assertEquals(song.getPath(), "songfiles/UntameableFire.mp3");

		song.playSong();
		assertEquals(song.getTimesPlayed(), 1);
		song.playSong();
		assertEquals(song.getTimesPlayed(), 2);
		
		LocalDate tomorrow = LocalDate.now().plusDays(1);
		song.playSong(tomorrow);
		assertEquals(song.getTimesPlayed(), 1);
	}
	
	@Test
	public void testPlaySongLimit() {
		SongQueue queue = new SongQueue();
		User user = new NormalUser("user", "2");
		assertTrue(queue.canPlaySong("Capture", user));
		assertTrue(queue.playSong("Capture", user));
		assertTrue(queue.canPlaySong("Capture", user));
		assertTrue(queue.playSong("Capture", user));
		assertTrue(queue.canPlaySong("Capture", user));
		assertTrue(queue.playSong("Capture", user));
		assertFalse(queue.canPlaySong("Capture", user));
		assertFalse(queue.playSong("Capture", user));

		assertFalse(queue.canPlaySong("LoopingString",user));
		assertFalse(queue.playSong("LoopingString", user));

		assertFalse(queue.canPlaySong("Capture", user));
		assertFalse(queue.playSong("Capture", user));

	}

	@Test
	public void testQueue() {
		SongQueue queue = new SongQueue();
		User user = new NormalUser("user", "2");
		queue.playSong("Capture", user);
		queue.playSong("LoopingString", user);
		queue.playSong("SwingCheese", user);
		assertFalse(queue.playSong("Capture", user));
		assertFalse(queue.playSong("LoopingString", user));

		assertEquals(queue.nextSong().getName(), "Capture");
		assertEquals(queue.nextSong().getName(), "LoopingString");
		assertEquals(queue.nextSong().getName(), "SwingCheese");
	

	}

	@Test
	public void testMultipleUsers(){
		SongQueue queue = new SongQueue();
		User user = new NormalUser("user", "2");
		User user2 = new NormalUser("user2", "2");
		User user3 = new NormalUser("user3", "2");
		User user4 = new NormalUser("user4", "2");

		assertTrue(queue.canPlaySong("Capture", user));
		assertTrue(queue.canPlaySong("Capture", user2));
		assertTrue(queue.playSong("Capture", user));
		
		assertTrue(queue.canPlaySong("Capture", user));
		assertTrue(queue.canPlaySong("Capture", user2));
		assertTrue(queue.playSong("Capture", user2));
		
		assertTrue(queue.canPlaySong("Capture", user));
		assertTrue(queue.canPlaySong("Capture", user2));
		assertTrue(queue.playSong("Capture", user2));
		
		assertFalse(queue.canPlaySong("Capture", user));
		assertFalse(queue.canPlaySong("Capture", user2));
		assertFalse(queue.playSong("Capture", user));
		assertFalse(queue.playSong("Capture", user2));    

		queue.playSong("LoopingString", user3);
		queue.playSong("SwingCheese", user);
		queue.playSong("LoopingString", user2);//user 2 already has played 3 songs
		assertFalse(queue.playSong("Capture", user));
		queue.playSong("LoopingString", user3);
		queue.playSong("LoopingString", user3);
		assertFalse(queue.playSong("LoopingString", user4));

		assertEquals(queue.nextSong().getName(), "Capture");
		assertEquals(queue.nextSong().getName(), "Capture");
		assertEquals(queue.nextSong().getName(), "Capture");
		assertEquals(queue.nextSong().getName(), "LoopingString");
		assertEquals(queue.nextSong().getName(), "SwingCheese");
		assertEquals(queue.nextSong().getName(), "LoopingString");
		assertEquals(queue.nextSong().getName(), "LoopingString");

	}
}