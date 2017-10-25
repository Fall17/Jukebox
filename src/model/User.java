package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * User stores data of certain users. This includes their user name, 
 * their passwords, how much playing time they have left and how
 *  many times they have played a song today.
 * 
 * @author Derian Davila Acuna
 *
 */
public abstract class User implements Serializable{
	private String username;
	private String password;
	private int timeLeft; //in seconds
	
	private LocalDate lastPlayed;
	private int timesPlayed = 0;
	
	/**
	 * Used in the creation of new users.
	 * Each new user is given 1500 minutes of playing time. 
	 */
	public User(String username, String password) {
		this.username = username; 
		this.password = password;
		timeLeft = 1500*60; //each user starts with 1500 minutes 
		lastPlayed = LocalDate.now().minusYears(20);
		timesPlayed = 0;
		
	}
	
	/**
	 * creates a user object using the specified values
	 * @param timeLeft the amount of play time the user has left
	 * @param lastPlayed the day the user last played a song
	 * @param timesPlayed how many times the user played any song today. If lastPlayed is not today then this field doesn't matter.
	 */
	public User(String username, String password, int timeLeft, LocalDate lastPlayed, int timesPlayed) {
		this.username = username; 
		this.password = password;
		this.timeLeft = timeLeft;
		this.lastPlayed = lastPlayed;
		this.timesPlayed = timesPlayed;
		
	}
	

	/**
	 * checks if the input string matches the password 
	 * @param password input string to check 
	 * @return returns true if the input and the user password matches else it returns false
	 */
	public boolean checkPassword(String password) {
		return password.equals(this.password);
	}
	
	/**
	 * returns a single string containing the values in user for file saving
	 * Format: username password timeleft lastPlayed timesPlayed
	 */
	public String toString() {
		return (username + " " + password + " " + timeLeft + " " + lastPlayed.toString() + " " + timesPlayed);
	}
	
	/**
	 * 
	 * returns the user's username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Determines whether the user can play a song today
	 * The user can not play a song if the song length is more then their remaining time or
	 * if they have played 3 songs today
	 * @param time length in seconds of the given song the user wants to play
	 * @return String of the given error message if the user can not play. Will return null if the user can play the song
	 */
	public String canPlaySong(int time) {
		if(time > timeLeft)
			return "User does not have enough time";
		if(lastPlayed.compareTo(LocalDate.now()) < 0) {
			return null;
		}
		else {
			//if the user has played 3 songs today you can not play a song
			if(timesPlayed > 2)
				return "User has played 3 songs today";
			else 
				return null;
		}
	}
	
	
	/**
	 * when user plays a song this subtracts the time length of the song from the overall time the user has left
	 * updates timesPlayed today and lastPlayed
	 * provides no error checking
	 * @param time length of the song being played
	 */
	public void playSong(int time) {
		timeLeft -= time;
		if(lastPlayed.isBefore(LocalDate.now()))
			timesPlayed = 0;
		lastPlayed = LocalDate.now();
		timesPlayed++;
	}
	
	//used to test playSong if a change in date resets timesPlayed 
	/**
	 * when user plays a song this subtracts the time length of the song from the overall time the user has left
	 * updates timesPlayed today and lastPlayed
	 * provides no error checking
	 * 
	 * @param time length of the song being played
	 * @param date the date you want to set lastPlayed to
	 */
	public void playSong(int time, LocalDate date) {
		timeLeft -= time;
		if(lastPlayed.isBefore(date))
			timesPlayed = 0;
		lastPlayed = date;
		timesPlayed++;
	}

}
