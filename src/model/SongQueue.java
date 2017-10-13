package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//Creates a FIFO queue of songs to be played
//checks that each song is only played at most 3 times a day
public class SongQueue {
	
	private List<Song> songs;
	private Queue<Song> songQueue;
	
	public SongQueue() {
		//songs contains the path to the song location
		songs = new ArrayList<>();
		songQueue = new LinkedList<Song>();
		//add the songs into the arrayList
		songs.add(new Song("songfiles/capture.mp3", "Capture", 5, "Pikachu"));
		songs.add(new Song("songfiles/DanseMacabreViolinHook.mp3", "DanseMacabreViolinHook", 34, "Kevin MacLeod"));
		songs.add(new Song("songfiles/DeterminedTumbao.mp3", "DeterminedTumbao", 20, "FreePlay Music"));
		//songs.add(new Song("songfiles/LongingInTheirHeart.mp3", "LongingInTheirHeart"));
		songs.add(new Song("songfiles/LoopingString.mp3", "LoopingString", 5, "Kevin MacLeod"));
		songs.add(new Song("songfiles/SwingCheese.mp3", "SwingCheese", 15, "FreePlay Music"));
		songs.add(new Song("songfiles/TheCurtainRises.mp3", "TheCurtainRises", 28, "Kevin MacLeod"));
		songs.add(new Song("songfiles/UntameableFire.mp3", "UntameableFire", 282, "Pierre Langer"));
		
	}
	
	//gets the SongPath for the given song name
	private Song getSong(String song) {
		for(Song curSong : songs) {
			if(curSong.getName().equals(song))
				return curSong;
		}
		return null;
	}
	
	public boolean canPlaySong(String song, User user) {
		Song curSong = getSong(song);
		//if the user has hit his/her limit on either number of songs or time limit then return false
		if(!user.canPlaySong(curSong.getSongLength()))
			return false;
		//if the song has now been played today
		if(curSong.getLastPlayed().isBefore(LocalDate.now())) {
			return true;
		}
		else {
			//if the song has been played 3 times today you can not play the song again
			if(curSong.getTimesPlayed() > 2)
				return false;
			else 
				return true;
		}
	}
	
	//adds the song to the queue if canPlaySong(song) is true
	public boolean playSong(String song, User user) {
		Song curSong = getSong(song);
		if(!canPlaySong(song, user))
			return false;
		
		songQueue.add(curSong);
		curSong.playSong();
		user.playSong(curSong.getSongLength());
		return true;
	}
	
	//returns the next element in the Queue and removes it from the queue
	public Song nextSong() {
		return songQueue.remove();
	}
}

