package model;

import java.time.LocalDate;

// Added to allow package model to exist on GitHub
public class Song {
		private LocalDate lastPlayed;
		private int timesPlayed;
	
		private String path;
		private String songName;
		private int songLength; //in seconds
		private String songLengthInMinutes;
		private String artist;
		
		public Song(String path, String songName, int songLength, String artist) {
			this.path = path;
			this.songName = songName;
			this.songLength = songLength;
			this.artist = artist;
			int songMinutes = songLength/60;
			int songSeconds = songLength%60;
			if(songSeconds >= 10) {
				songLengthInMinutes = "" + songMinutes + ":" + songSeconds;
			}
			else {
				songLengthInMinutes = "" + songMinutes + ":0" + songSeconds;
			}
			lastPlayed = LocalDate.now().minusYears(20);
			timesPlayed = 0;
		}
		
		public String getPath() {
			return path;
		}
		
		public String getName() {
			return songName;
		}
		
		public LocalDate getLastPlayed() {
			return lastPlayed;
		}
		
		public int getTimesPlayed() {
			return timesPlayed;
		}
		
		public int getSongLength() {
			return songLength;
		}
		
		public String getSongLengthInMinutes() {
			return songLengthInMinutes;
		}
		
		public String getArtist() {
			return artist;
		}
		
		//increments timesPlayed and changes the lastPlayed date to now;
		//does no checking on whether you can play the song
		public void playSong() {
			if(lastPlayed.isBefore(LocalDate.now()))
				timesPlayed = 0;
			lastPlayed = LocalDate.now();
			timesPlayed++;
		}
		
		//used to test to see if the change in date resets the timesPlayed
		public void playSong(LocalDate date) {
			if(date.isBefore(lastPlayed))
				timesPlayed = 0;
			lastPlayed = date;
			timesPlayed++;
		}
	}
