package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Used to store song data. This includes the path, the song name, 
 * how long the song is, and who the artist is.
 * 
 * @author Derian Davila Acuna
 *
 */
public class Song implements Serializable {
		private LocalDate lastPlayed;
		private int timesPlayed;
	
		private String path;
		private String songName;
		private int songLength; //in seconds
		private String artist;
		
		public Song(String path, String songName, int songLength, String artist) {
			this.path = path;
			this.songName = songName;
			this.songLength = songLength;
			this.artist = artist;
			lastPlayed = LocalDate.now().minusYears(20);
			timesPlayed = 0;
		}
		
		/**
		 * @return the path where the song is located 
		 */
		public String getPath() {
			return path;
		}
		
		/**
		 * 
		 * @return the title of the song
		 */
		public String getName() {
			return songName;
		}
		
		/**
		 * 
		 * @return the date the song was last played
		 */
		public LocalDate getLastPlayed() {
			return lastPlayed;
		}
		
		/**
		 * 
		 * @return The amount of times the song was played on the last played date.
		 */
		public int getTimesPlayed() {
			return timesPlayed;
		}
		
		/**
		 * 
		 * @return how long the song is in seconds
		 */
		public int getSongLength() {
			return songLength;
		}
		
		/**
		 * 
		 * @return the artist of the song
		 */
		public String getArtist() {
			return artist;
		}
		
		/**
		 * This increments the amount of times this song has been played today
		 * and changes the last played date to now.
		 */
		public void playSong() {
			if(lastPlayed.isBefore(LocalDate.now()))
				timesPlayed = 0;
			lastPlayed = LocalDate.now();
			timesPlayed++;
		}
		
		/**
		 * This increments the amount of times this song has been played on the given date
		 * and changes the last played date to date
		 * @param date the date you want to set the song's last played date
		 */
		public void playSong(LocalDate date) {
			if(date.isBefore(lastPlayed))
				timesPlayed = 0;
			lastPlayed = date;
			timesPlayed++;
		}
	}
