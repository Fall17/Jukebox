package model;

import java.time.LocalDate;

public abstract class User {
	private String username;
	private String password;
	private int timeLeft; //in seconds
	
	private LocalDate lastPlayed;
	private int timesPlayed = 0;
	
	//user class takes in a username and a password
	public User(String username, String password) {
		this.username = username; 
		this.password = password;
		timeLeft = 1500*60; //each user starts with 1500 minutes 
		lastPlayed = LocalDate.now().minusYears(20);
		timesPlayed = 0;
		
	}
	
	//checks if the input string matchs the password
	public boolean checkPassword(String password) {
		return password.equals(this.password);
	}
	
	//gets the username
	public String getUsername() {
		return username;
	}
	
	//returns true if the user can play a song and if the user hasn't played more then 3 songs today
	//	(if the length of the song is less then the timeLeft of the user)
	//else returns false 
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
