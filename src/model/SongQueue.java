package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javafx.scene.control.TableColumn;


/**
 * Creates a FIFO queue of songs to be played
 * It also checks that the song can be played that day, is played less then 3 times. 
 * And it checks to make sure that the user can play the given song.
 * 
 * @author Derian Davila Acuna
 *
 */
public class SongQueue implements Serializable {

	private List<Song> songs;
	private Queue<Song> songQueue;

	private File userFile;

	/**
	 * If their is a valid save file then it will use that save file to create a list of songs and the song queue
	 * Otherwise it will use the default list of songs and create a new song queue
	 */
	public SongQueue() {
		userFile = new File("saveFiles/SongQueue.txt");
		//reads any possible save file if not it creates one
		try {
			if(!userFile.createNewFile())
				readInput();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		if(songs == null) {
			createNewSongList();
		}
		if(songQueue == null)
			songQueue = new LinkedList<Song>();
	}

	/**
	 *  createNewSongList creates a new songQueue and a new songs array list. 
	 *  It is called when the SongQueue save file is blank or when there was no SongQueue to begin with.
	 */
	private void createNewSongList() {
		//set up the default list of songs to the list
		songs = new ArrayList<>();
		//add the songs into the arrayList
		songs.add(new Song("songfiles/capture.mp3", "Capture", 5, "Pikachu"));
		songs.add(new Song("songfiles/DanseMacabreViolinHook.mp3", "DanseMacabreViolinHook", 34, "Kevin MacLeod"));
		songs.add(new Song("songfiles/DeterminedTumbao.mp3", "DeterminedTumbao", 20, "FreePlay Music"));
		songs.add(new Song("songfiles/LopingSting.mp3", "LopingSting", 5, "Kevin MacLeod"));
		songs.add(new Song("songfiles/SwingCheese.mp3", "SwingCheese", 15, "FreePlay Music"));
		songs.add(new Song("songfiles/TheCurtainRises.mp3", "TheCurtainRises", 28, "Kevin MacLeod"));
		songs.add(new Song("songfiles/UntameableFire.mp3", "UntameableFire", 282, "Pierre Langer"));		
	}

	/**
	 * 
	 * @param song the title of the song
	 * @return the Song corresponding to the given title
	 */
	private Song getSong(String song) {
		for(Song curSong : songs) {
			if(curSong.getName().equals(song))
				return curSong;
		}
		return null;
	}

	/**
	 * 
	 * @param song the title of the song
	 * @param user the user trying to play the song
	 * @return The error message if the user can't play the song. Returns null if the user can.
	 */
	public String canPlaySong(String song, User user) {
		Song curSong = getSong(song);
		//if the user has hit his/her limit on either number of songs or time limit then return false
		if(user.canPlaySong(curSong.getSongLength()) != null)
			return user.canPlaySong(curSong.getSongLength());
		//if the song has now been played today
		if(curSong.getLastPlayed().isBefore(LocalDate.now())) {
			return null;
		}
		else {
			//if the song has been played 3 times today you can not play the song again
			if(curSong.getTimesPlayed() > 2)
				return "Error: Song Plays > 3";
					   
			else 
				return null;
		}
	}
	
	/**
	 * 
	 * @param song the title of the song
	 * @param user the user trying to play the song
	 * @return The error message if the user can't play the song. Returns null if the user can.
	 */
	public String playSong(String song, User user) {
		Song curSong = getSong(song);
		if(canPlaySong(song, user) != null)
			return canPlaySong(song,user);

		songQueue.add(curSong);
		curSong.playSong();
		user.playSong(curSong.getSongLength());
		return null;
	}
	
	
	/**
	 * @return returns the next song in the queue and removes it from the queue
	 */
	public Song nextSong() {
		return songQueue.remove();
	}
	
	/**
	 *
	 * @return  returns the next song in the queue without removing it
	 */
	public Song peekNextSong() {
		return songQueue.peek();
	}

	/**
	 * @return the list of songs
	 */
	public List<Song> getSongList(){
		return songs;
	}
	
	/**
	 * @return Returns the queue in an arrayList. If their is no queue then it returns null
	 */
	public List<Song> getQueueList(){
		if(songQueue.size() == 0)
			return null;
		return new ArrayList<Song>(songQueue);
	}
	
	
	/**
	 * //reads the song list and song queue from the save file
	 */
	private void readInput() {
		try {

			FileInputStream input = new FileInputStream(userFile);
			ObjectInputStream objInput = new ObjectInputStream(input);
			songs = (List<Song>) objInput.readObject();
			songQueue = (Queue<Song>) objInput.readObject();
			objInput.close();
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * writes the song list and the save queue into the save file
	 */
	public void writeToFile() {
		try {
			FileOutputStream output = new FileOutputStream(userFile);
			ObjectOutputStream objOutput = new ObjectOutputStream(output);
			objOutput.writeObject(songs);
			objOutput.writeObject(songQueue);
			objOutput.close();
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

