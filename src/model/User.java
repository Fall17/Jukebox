package model;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class User implements Serializable{
	private String username;
	private String password;
	private int timeLeft; //in seconds
	
	private LocalDate lastPlayed;
	private int timesPlayed = 0;
	
	//user class takes in a username and a password
	//used for new users
	public User(String username, String password) {
		this.username = username; 
		this.password = password;
		timeLeft = 1500*60; //each user starts with 1500 minutes 
		lastPlayed = LocalDate.now().minusYears(20);
		timesPlayed = 0;
		
	}
	
	//creates a user from an already existing user that was saved to a file
	public User(String username, String password, int timeLeft, LocalDate lastPlayed, int timesPlayed) {
		this.username = username; 
		this.password = password;
		this.timeLeft = timeLeft;
		this.lastPlayed = lastPlayed;
		this.timesPlayed = timesPlayed;
		
	}
	
	//checks if the input string matchs the password
	public boolean checkPassword(String password) {
		return password.equals(this.password);
	}
	
	//returns a single string containing the values in user for file saving
	//Format: username password timeleft lastPlayed timesPlayed
	public String toString() {
		return (username + " " + password + " " + timeLeft + " " + lastPlayed.toString() + " " + timesPlayed);
	}
	
	//gets the username
	public String getUsername() {
		return username;
	}
	
	/**
	 * 
	 * @param time length in seconds of the given song the user wants to play
	 * @return String of the given error message if the user can not play. Will return null if the user can play the song
	 */
	/*public String canPlaySong(int time) {
		if(time > timeLeft)
			return "User does not have any enough time left to play the song";
		if(lastPlayed.compareTo(LocalDate.now()) < 0) {
			return null;
		}
		else {
			//if the user has played 3 songs today you can not play a song
			if(timesPlayed > 2)
				return "User has already played 3 songs today";
			else 
				return null;
		}
	}*/
	
	public boolean canPlaySong(int time) {
		if(time > timeLeft)
			return false;
		if(lastPlayed.compareTo(LocalDate.now()) < 0) {
			return true;
		}
		else {
			//if the user has played 3 songs today you can not play a song
			if(timesPlayed > 2)
				return false;
			else 
				return true;
		}
	}
	
	
	//when user plays a song this subtracts the time length of the song from the overall time the user has left
	// updates timesPlayed today and lastPlayed
	// provides no error checking
	public void playSong(int time) {
		timeLeft -= time;
		if(lastPlayed.isBefore(LocalDate.now()))
			timesPlayed = 0;
		lastPlayed = LocalDate.now();
		timesPlayed++;
	}
	
	//used to test playSong if a change in date resets timesPlayed 
	public void playSong(int time, LocalDate date) {
		timeLeft -= time;
		if(lastPlayed.isBefore(date))
			timesPlayed = 0;
		lastPlayed = date;
		timesPlayed++;
	}

}
