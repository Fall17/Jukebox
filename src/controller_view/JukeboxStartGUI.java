// Author: Andrew Marrufo
// Partner: Derian Acuna

package controller_view;

import java.io.File;
import java.net.URI;
import java.util.NoSuchElementException;

/**
 * This program is a functional spike to determine the interactions are 
 * actually working. It is an event-driven program with a graphical user
 * interface to affirm the functionality all Iteration 1 tasks have been 
 * completed and are working correctly. This program will be used to 
 * test your code for the first 100 points of the JukeBox project.
 */
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.AdminUser;
import model.Song;
import model.SongQueue;
import model.User;
import model.Users;

public class JukeboxStartGUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private Users users;
	private User currUser;
	private String currUserName;
	private SongQueue songQueue;
	private TextField accountNameField;
	private TextField passwordField;
	private ChoiceBox songChoice;
	private MediaPlayer jukebox;
	private boolean jukeboxInUse;
	private BorderPane all;

	// initializes GUI
	@Override
	public void start(Stage primaryStage) {
		users = new Users();
		songQueue = new SongQueue();
		currUser = null;
		currUserName = null;
		jukeboxInUse = false;

		all = new BorderPane();
		// dummy pane
		GridPane top = new GridPane();
		top.setHgap(10);
		top.setVgap(10);
		all.setTop(top);

		// "center" GridPane holds the login labels and text fields
		GridPane center = new GridPane();
		center.setHgap(10);
		center.setVgap(10);
		Label accountName = new Label("Account Name");
		Label password = new Label("Password");
		accountNameField = new TextField();
		passwordField = new TextField();
		center.add(accountName, 4, 5);
		center.add(password, 4, 6);
		center.add(accountNameField, 5, 5);
		center.add(passwordField, 5, 6);
		all.setCenter(center);

		// "bottom" GridPane holds the "login" button
		GridPane bottom = new GridPane();
		bottom.setHgap(10);
		bottom.setVgap(10);
		Button loginButton = new Button("Login");
		EventHandler<ActionEvent> handleLogin = new loginHandler();
		loginButton.setOnAction(handleLogin);
		bottom.add(loginButton, 13, 0);

		all.setBottom(bottom);

		Scene scene = new Scene(all, 300, 200);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// shows GUI when no user is logged in
	private void showLoggedOut() {
		// dummy pane
		GridPane top = new GridPane();
		top.setHgap(10);
		top.setVgap(10);
		all.setTop(top);

		// "center" GridPane holds the login labels and text fields
		GridPane center = new GridPane();
		center.setHgap(10);
		center.setVgap(10);
		Label accountName = new Label("Account Name");
		Label password = new Label("Password");
		accountNameField = new TextField();
		passwordField = new TextField();
		center.add(accountName, 4, 5);
		center.add(password, 4, 6);
		center.add(accountNameField, 5, 5);
		center.add(passwordField, 5, 6);
		all.setCenter(center);

		// "bottom" GridPane holds the "login" button
		GridPane bottom = new GridPane();
		bottom.setHgap(10);
		bottom.setVgap(10);
		Button loginButton = new Button("Login");
		EventHandler<ActionEvent> handleLogin = new loginHandler();
		loginButton.setOnAction(handleLogin);
		bottom.add(loginButton, 13, 0);

		all.setBottom(bottom);
	}

	// shows GUI when a normal (non-admin) user is logged in
	private void showLoggedInUser() {
		// "top" GridPane holds the song selection components
		GridPane top = new GridPane();
		top.setHgap(10);
		top.setVgap(10);
		songChoice = new ChoiceBox(FXCollections.observableArrayList("Capture", "DanseMacabreViolinHook",
				"DeterminedTumbao", "LopingSting", "SwingCheese", "TheCurtainRises", "UntameableFire"));
		songChoice.getSelectionModel().selectFirst();
		top.add(songChoice, 3, 0);
		Button playSong = new Button("Play");
		EventHandler<ActionEvent> handleSong1 = new songHandler();
		playSong.setOnAction(handleSong1);
		top.add(playSong, 4, 0);
		all.setTop(top);

		// "center" GridPane shows the username and song status
		GridPane center = new GridPane();
		center.setHgap(10);
		center.setVgap(10);
		Label status = new Label("Select a song");
		Label accountName = new Label("Account Name: " + currUserName);
		center.add(status, 10, 2);
		center.add(accountName, 10, 6);
		all.setCenter(center);

		// "bottom" GridPane holds the "log out" button
		GridPane bottom = new GridPane();
		bottom.setHgap(10);
		bottom.setVgap(10);
		Button logoutButton = new Button("Log out");
		EventHandler<ActionEvent> handleLogout = new logoutHandler();
		logoutButton.setOnAction(handleLogout);
		bottom.add(logoutButton, 12, 2);

		all.setBottom(bottom);
	}

	// shows GUI when a normal user cannot play a song (for whatever reason)
	private void showLoggedInUserSongError() {
		// "top" GridPane holds the song selection components
		GridPane top = new GridPane();
		top.setHgap(10);
		top.setVgap(10);
		songChoice = new ChoiceBox(FXCollections.observableArrayList("Capture", "DanseMacabreViolinHook",
				"DeterminedTumbao", "LopingSting", "SwingCheese", "TheCurtainRises", "UntameableFire"));
		songChoice.getSelectionModel().selectFirst();
		top.add(songChoice, 3, 0);
		Button playSong = new Button("Play");
		EventHandler<ActionEvent> handleSong1 = new songHandler();
		playSong.setOnAction(handleSong1);
		top.add(playSong, 4, 0);
		all.setTop(top);

		// "center" GridPane shows the username and song status
		GridPane center = new GridPane();
		center.setHgap(10);
		center.setVgap(10);
		Label status = new Label("Error: Could not add song!");
		Label accountName = new Label("Account Name: " + currUserName);
		center.add(status, 10, 2);
		center.add(accountName, 10, 6);
		all.setCenter(center);

		// "bottom" GridPane holds the "log out" button
		GridPane bottom = new GridPane();
		bottom.setHgap(10);
		bottom.setVgap(10);
		Button logoutButton = new Button("Log out");
		EventHandler<ActionEvent> handleLogout = new logoutHandler();
		logoutButton.setOnAction(handleLogout);
		bottom.add(logoutButton, 12, 2);

		all.setBottom(bottom);
	}

	// shows GUI when admin user is logged in
	private void showLoggedInAdmin() {
		// "top" GridPane holds the song selection components
		GridPane top = new GridPane();
		top.setHgap(10);
		top.setVgap(10);
		songChoice = new ChoiceBox(FXCollections.observableArrayList("Capture", "DanseMacabreViolinHook",
				"DeterminedTumbao", "LopingSting", "SwingCheese", "TheCurtainRises", "UntameableFire"));
		songChoice.getSelectionModel().selectFirst();
		top.add(songChoice, 3, 0);
		Button playSong = new Button("Play");
		EventHandler<ActionEvent> handleSong1 = new songHandler();
		playSong.setOnAction(handleSong1);
		top.add(playSong, 4, 0);
		all.setTop(top);

		// "center" GridPane holds the add/remove components (and song status)
		GridPane center = new GridPane();
		center.setHgap(10);
		center.setVgap(10);
		Label accountName = new Label("Account Name");
		Label password = new Label("Password");
		accountNameField = new TextField();
		passwordField = new TextField();
		Label status = new Label("Select a song");
		center.add(status, 5, 1);
		center.add(accountName, 4, 3);
		center.add(password, 4, 4);
		center.add(accountNameField, 5, 3);
		center.add(passwordField, 5, 4);
		all.setCenter(center);

		// "bottom" GridPane holds the "log out" button and add/remove buttons
		GridPane bottom = new GridPane();
		bottom.setHgap(10);
		bottom.setVgap(10);
		Button addButton = new Button("Add User");
		EventHandler<ActionEvent> handleAdd = new addHandler();
		addButton.setOnAction(handleAdd);
		Button removeButton = new Button("Remove User");
		EventHandler<ActionEvent> handleRemove = new removeHandler();
		removeButton.setOnAction(handleRemove);
		Button logoutButton = new Button("Log out");
		EventHandler<ActionEvent> handleLogout = new logoutHandler();
		logoutButton.setOnAction(handleLogout);
		bottom.add(addButton, 10, 0);
		bottom.add(removeButton, 11, 0);
		bottom.add(logoutButton, 10, 1);

		all.setBottom(bottom);
	}

	// shows GUI when admin user cannot play a song (for whatever reason)
	private void showLoggedInAdminSongError() {
		// "top" GridPane holds the song selection buttons
		GridPane top = new GridPane();
		top.setHgap(10);
		top.setVgap(10);
		songChoice = new ChoiceBox(FXCollections.observableArrayList("Capture", "DanseMacabreViolinHook",
				"DeterminedTumbao", "LopingSting", "SwingCheese", "TheCurtainRises", "UntameableFire"));
		songChoice.getSelectionModel().selectFirst();
		top.add(songChoice, 3, 0);
		Button playSong = new Button("Play");
		EventHandler<ActionEvent> handleSong1 = new songHandler();
		playSong.setOnAction(handleSong1);
		top.add(playSong, 4, 0);
		all.setTop(top);

		// "center" GridPane holds the add/remove components (and song status)
		GridPane center = new GridPane();
		center.setHgap(10);
		center.setVgap(10);
		Label accountName = new Label("Account Name");
		Label password = new Label("Password");
		accountNameField = new TextField();
		passwordField = new TextField();
		Label status = new Label("Error: Could not add song!");
		center.add(status, 5, 1);
		center.add(accountName, 4, 3);
		center.add(password, 4, 4);
		center.add(accountNameField, 5, 3);
		center.add(passwordField, 5, 4);
		all.setCenter(center);

		// "bottom" GridPane holds the "log out" button and add/remove buttons
		GridPane bottom = new GridPane();
		bottom.setHgap(10);
		bottom.setVgap(10);
		Button addButton = new Button("Add User");
		EventHandler<ActionEvent> handleAdd = new addHandler();
		addButton.setOnAction(handleAdd);
		Button removeButton = new Button("Remove User");
		EventHandler<ActionEvent> handleRemove = new removeHandler();
		removeButton.setOnAction(handleRemove);
		Button logoutButton = new Button("Log out");
		EventHandler<ActionEvent> handleLogout = new logoutHandler();
		logoutButton.setOnAction(handleLogout);
		bottom.add(addButton, 10, 0);
		bottom.add(removeButton, 11, 0);
		bottom.add(logoutButton, 10, 1);

		all.setBottom(bottom);
	}

	// handles a user attempting to queue up a song
	private class songHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			// if there is no user logged in (just in case)
			if (currUser == null) {
			}
			// if user is logged in
			else {
				// attempts to play song
				boolean didPlaySong = songQueue.playSong(songChoice.getSelectionModel().getSelectedItem().toString(),
						currUser);
				if (didPlaySong) {
					// if jukebox is in use, no further action necessary
					if (jukeboxInUse) {
					}
					// sets up jukebox if not in use
					else {
						Song nextSong = songQueue.nextSong();
						File file = new File(nextSong.getPath());
						URI uri = file.toURI();
						jukebox = new MediaPlayer(new Media(uri.toString()));
						jukebox.setAutoPlay(true);
						jukebox.play();
						jukebox.setOnEndOfMedia(new EndOfSongHandler());
						jukeboxInUse = true;
					}
					if (currUser instanceof AdminUser) {
						showLoggedInAdmin();
					} else {
						showLoggedInUser();
					}
				} else {
					// shows that song could not be played
					if (currUser instanceof AdminUser) {
						showLoggedInAdminSongError();
					} else {
						showLoggedInUserSongError();
					}
				}
			}
		}
	}

	// handles clicking the login button
	private class loginHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			currUser = users.logIn(accountNameField.getText(), passwordField.getText());
			// if user is logged in (just in case)
			if (currUser == null) {
			} else {
				currUserName = accountNameField.getText();
				// checks if admin user; if so, shows admin features
				if (currUser instanceof AdminUser) {
					showLoggedInAdmin();
				}
				// if not, shows normal user features
				else {
					showLoggedInUser();
				}
			}
		}
	}

	// handles a user clicking the logout button
	private class logoutHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			currUser = null;
			currUserName = null;
			showLoggedOut();
		}
	}

	// handles an admin user attempting to add another user
	private class addHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			users.addUser(accountNameField.getText(), passwordField.getText());
			showLoggedInAdmin();
		}
	}

	// handles an admin user attempting to remove a user
	private class removeHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			users.removeUser(accountNameField.getText());
			showLoggedInAdmin();
		}
	}

	// handles when a song ends in the jukebox (recursive, breaks when no song
	// left in queue)
	private class EndOfSongHandler implements Runnable {
		@Override
		public void run() {
			Song nextSong;
			// tries to get next song in queue
			// if there is no song in queue, catches exception
			try {
				nextSong = songQueue.nextSong();
			} catch (NoSuchElementException e) {
				nextSong = null;
			}
			// if there is no song in queue, does nothing
			if (nextSong == null) {
				jukeboxInUse = false;
			}
			// if there is song in queue, plays next song and recurses
			else {
				File file = new File(nextSong.getPath());
				URI uri = file.toURI();
				jukebox = new MediaPlayer(new Media(uri.toString()));
				jukebox.setAutoPlay(true);
				jukebox.play();
				jukebox.setOnEndOfMedia(new EndOfSongHandler());
			}
		}
	}
}