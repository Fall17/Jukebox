package controller_view;

import java.io.File;
import java.net.URI;
import java.util.NoSuchElementException;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.AdminUser;
import model.Song;
import model.SongQueue;
import model.User;
import model.Users;

/**
 * This program is a functional spike to determine the interactions are 
 * actually working. It is an event-driven program with a graphical user
 * interface to affirm the functionality all Iteration 2 tasks have been 
 * completed and are working correctly.
 * 
 * @author Andrew Marrufo
 */
public class JukeboxStartGUI extends Application {

	/**
	 * Main method.
	 * 
	 * @param args standard args array for main methods
	 */
	public static void main(String[] args) {
		launch(args);
	}

	private Users users;
	private User currUser;
	private String currUserName;
	private SongQueue songQueue;
	private TextField accountNameField;
	private TextField passwordField;
	private MediaPlayer jukebox;
	private boolean jukeboxInUse;
	private SongViewer songViewer;
	private BorderPane all;
	private ListView songQueueList;

	/**
	 * Initializes the GUI.
	 * 
	 * @param primaryStage the stage that the GUI will be drawn on
	 */
	@Override
	public void start(Stage primaryStage) {
		users = new Users();
		songQueue = new SongQueue();
		currUser = null;
		currUserName = null;
		jukeboxInUse = false;
		songViewer = new SongViewer();
		songQueueList = new ListView();
		songQueueList.setPrefWidth(160);

		all = new BorderPane();
		// adds "Song Queue" label
		GridPane top = new GridPane();
		top.setHgap(10);
		top.setVgap(10);
		Label queue = new Label("Song Queue");
		top.add(queue, 69, 0);

		all.setTop(top);

		// "center" GridPane holds the login labels and text fields and login button
		GridPane center = new GridPane();
		center.setHgap(10);
		center.setVgap(10);
		Label accountName = new Label("Account Name");
		Label password = new Label("Password");
		accountNameField = new TextField();
		passwordField = new TextField();
		Button loginButton = new Button("Login");
		EventHandler<ActionEvent> handleLogin = new loginHandler();
		loginButton.setOnAction(handleLogin);
		center.add(accountName, 2, 5);
		center.add(password, 2, 6);
		center.add(accountNameField, 3, 5);
		center.add(passwordField, 3, 6);
		center.add(loginButton, 3, 7);
		all.setCenter(center);

		// dummy pane
		GridPane bottom = new GridPane();
		bottom.setHgap(10);
		bottom.setVgap(10);

		all.setBottom(bottom);
		all.setLeft(songViewer);
		all.setRight(songQueueList);

		primaryStage.setOnCloseRequest(new WritePersistentObject());
		
		Scene scene = new Scene(all, 800, 250);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Shows GUI when no user is logged in.
	 */
	private void showLoggedOut() {
		// adds "Song Queue" label
		GridPane top = new GridPane();
		top.setHgap(10);
		top.setVgap(10);
		Label queue = new Label("Song Queue");
		top.add(queue, 69, 0);
		all.setTop(top);

		// "center" GridPane holds the login labels and text fields and login button
		GridPane center = new GridPane();
		center.setHgap(10);
		center.setVgap(10);
		Label accountName = new Label("Account Name");
		Label password = new Label("Password");
		accountNameField = new TextField();
		passwordField = new TextField();
		Button loginButton = new Button("Login");
		EventHandler<ActionEvent> handleLogin = new loginHandler();
		loginButton.setOnAction(handleLogin);
		center.add(accountName, 2, 5);
		center.add(password, 2, 6);
		center.add(accountNameField, 3, 5);
		center.add(passwordField, 3, 6);
		center.add(loginButton, 3, 7);
		all.setCenter(center);

		// dummy pane
		GridPane bottom = new GridPane();
		bottom.setHgap(10);
		bottom.setVgap(10);

		all.setBottom(bottom);
		all.setLeft(songViewer);
		all.setRight(songQueueList);
	}

	/**
	 * Shows GUI when a normal (non-admin) user is logged in.
	 */
	private void showLoggedInUser() {
		// "top" GridPane holds the "Play" button and the"Song Queue" label
		GridPane top = new GridPane();
		top.setHgap(10);
		top.setVgap(10);
		Button playSong = new Button("Play");
		EventHandler<ActionEvent> handleSong1 = new songHandler();
		playSong.setOnAction(handleSong1);
		top.add(playSong, 15, 0);
		Label queue = new Label("Song Queue");
		top.add(queue, 65, 0);
		all.setTop(top);

		// "center" GridPane shows the username, song status, and logout button
		GridPane center = new GridPane();
		center.setHgap(10);
		center.setVgap(10);
		Label status = new Label("Select a song");
		Label accountName = new Label("Account Name: " + currUserName);
		Button logoutButton = new Button("Log out");
		EventHandler<ActionEvent> handleLogout = new logoutHandler();
		logoutButton.setOnAction(handleLogout);
		center.add(status, 10, 2);
		center.add(accountName, 10, 6);
		center.add(logoutButton, 10, 10);
		all.setCenter(center);

		// dummy pane
		GridPane bottom = new GridPane();
		bottom.setHgap(10);
		bottom.setVgap(10);

		all.setBottom(bottom);
		all.setLeft(songViewer);
		all.setRight(songQueueList);
	}

	/**
	 * Shows GUI when a normal user cannot play a song (for whatever reason)..
	 * 
	 * @param errorMessage is the given error message for why the user can't play the selected song
	 */
	private void showLoggedInUserSongError(String errorMessage) {
		// "top" GridPane holds the "Play" button and the"Song Queue" label
		GridPane top = new GridPane();
		top.setHgap(10);
		top.setVgap(10);
		Button playSong = new Button("Play");
		EventHandler<ActionEvent> handleSong1 = new songHandler();
		playSong.setOnAction(handleSong1);
		top.add(playSong, 15, 0);
		Label queue = new Label("Song Queue");
		top.add(queue, 65, 0);
		all.setTop(top);

		// "center" GridPane shows the username, song status, and logout button
		GridPane center = new GridPane();
		center.setHgap(10);
		center.setVgap(10);
		Label status = new Label(errorMessage);
		Label accountName = new Label("Account Name: " + currUserName);
		Button logoutButton = new Button("Log out");
		EventHandler<ActionEvent> handleLogout = new logoutHandler();
		logoutButton.setOnAction(handleLogout);
		center.add(status, 10, 2);
		center.add(accountName, 10, 6);
		center.add(logoutButton, 10, 10);
		all.setCenter(center);

		// dummy pane
		GridPane bottom = new GridPane();
		bottom.setHgap(10);
		bottom.setVgap(10);

		all.setBottom(bottom);
		all.setLeft(songViewer);
		all.setRight(songQueueList);
	}

	/**
	 * Shows GUI when admin user is logged in.
	 */
	private void showLoggedInAdmin() {
		// "top" GridPane holds the "Play" button and the"Song Queue" label
		GridPane top = new GridPane();
		top.setHgap(10);
		top.setVgap(10);
		Button playSong = new Button("Play");
		EventHandler<ActionEvent> handleSong1 = new songHandler();
		playSong.setOnAction(handleSong1);
		top.add(playSong, 15, 0);
		Label queue = new Label("Song Queue");
		top.add(queue, 65, 0);
		all.setTop(top);

		// "center" GridPane holds the add/remove components, song status, and logout
		// button
		GridPane center = new GridPane();
		center.setHgap(10);
		center.setVgap(10);
		Label accountName = new Label("Account Name");
		Label password = new Label("Password");
		accountNameField = new TextField();
		passwordField = new TextField();
		Label status = new Label("Select a song");
		Button addButton = new Button("Add User");
		EventHandler<ActionEvent> handleAdd = new addHandler();
		addButton.setOnAction(handleAdd);
		Button removeButton = new Button("Remove User");
		EventHandler<ActionEvent> handleRemove = new removeHandler();
		removeButton.setOnAction(handleRemove);
		Button logoutButton = new Button("Log out");
		EventHandler<ActionEvent> handleLogout = new logoutHandler();
		logoutButton.setOnAction(handleLogout);
		center.add(status, 5, 1);
		center.add(accountName, 4, 3);
		center.add(password, 4, 4);
		center.add(accountNameField, 5, 3);
		center.add(passwordField, 5, 4);
		center.add(addButton, 4, 5);
		center.add(removeButton, 5, 5);
		center.add(logoutButton, 4, 7);
		all.setCenter(center);

		// dummy pane
		GridPane bottom = new GridPane();
		bottom.setHgap(10);
		bottom.setVgap(10);

		all.setBottom(bottom);
		all.setLeft(songViewer);
		all.setRight(songQueueList);
	}

	/**
	 * Shows GUI when admin user cannot play a song (for whatever reason).
	 * 
	 * @param errorMessage is the given error message for why the user can't play the selected song
	 */
	private void showLoggedInAdminSongError(String errorMessage) {
		// "top" GridPane holds the "Play" button and the"Song Queue" label
		GridPane top = new GridPane();
		top.setHgap(10);
		top.setVgap(10);
		Button playSong = new Button("Play");
		EventHandler<ActionEvent> handleSong1 = new songHandler();
		playSong.setOnAction(handleSong1);
		top.add(playSong, 15, 0);
		Label queue = new Label("Song Queue");
		top.add(queue, 65, 0);
		all.setTop(top);

		// "center" GridPane holds the add/remove components, song status, and logout
		// button
		GridPane center = new GridPane();
		center.setHgap(10);
		center.setVgap(10);
		Label accountName = new Label("Account Name");
		Label password = new Label("Password");
		accountNameField = new TextField();
		passwordField = new TextField();
		Label status = new Label(errorMessage);
		Button addButton = new Button("Add User");
		EventHandler<ActionEvent> handleAdd = new addHandler();
		addButton.setOnAction(handleAdd);
		Button removeButton = new Button("Remove User");
		EventHandler<ActionEvent> handleRemove = new removeHandler();
		removeButton.setOnAction(handleRemove);
		Button logoutButton = new Button("Log out");
		EventHandler<ActionEvent> handleLogout = new logoutHandler();
		logoutButton.setOnAction(handleLogout);
		center.add(status, 5, 1);
		center.add(accountName, 4, 3);
		center.add(password, 4, 4);
		center.add(accountNameField, 5, 3);
		center.add(passwordField, 5, 4);
		center.add(addButton, 4, 5);
		center.add(removeButton, 5, 5);
		center.add(logoutButton, 4, 7);
		all.setCenter(center);

		// dummy pane
		GridPane bottom = new GridPane();
		bottom.setHgap(10);
		bottom.setVgap(10);

		all.setBottom(bottom);
		all.setLeft(songViewer);
		all.setRight(songQueueList);
	}

	/**
	 * handles a user attempting to queue up a song
	 * 
	 * @author Andrew Marrufo
	 */
	private class songHandler implements EventHandler<ActionEvent> {
		@SuppressWarnings("unchecked")
		@Override
		/**
		 * Handler for ActionEvent
		 *
		 * @param event the given ActionEvent
		 */
		public void handle(ActionEvent event) {
			// if there is no user logged in (just in case)
			if (currUser == null) {
			}
			// if user is logged in
			else {
				Song songToPlay = songViewer.getSelectionModel().getSelectedItem();
				// attempts to play song
				//if playSong returns a null then play song is successful
				String playSongErrorMessage = songQueue.playSong(songToPlay.getName(), currUser);
				if (playSongErrorMessage == null) {
					// if jukebox is in use, no further action necessary
					if (jukeboxInUse) {
						songQueueList.getItems().add(songToPlay.getName());
					}
					// sets up jukebox if not in use
					else {
						Song nextSong = songQueue.peekNextSong();
						File file = new File(nextSong.getPath());
						URI uri = file.toURI();
						jukebox = new MediaPlayer(new Media(uri.toString()));
						jukebox.setAutoPlay(true);
						jukebox.play();
						songQueueList.getItems().add(nextSong.getName());
						jukebox.setOnEndOfMedia(new EndOfSongHandler());
						jukeboxInUse = true;
					}
					// updates songViewer
					songViewer.getSelectionModel().getSelectedItem().playSong();
					songViewer.refresh();
					if (currUser instanceof AdminUser) {
						showLoggedInAdmin();
					} else {
						showLoggedInUser();
					}
				} else {
					// shows that song could not be played
					if (currUser instanceof AdminUser) {
						showLoggedInAdminSongError(playSongErrorMessage);
					} else {
						showLoggedInUserSongError(playSongErrorMessage);
					}
				}
			}
		}
	}

	/**
	 * handles clicking the login button
	 * 
	 * @author Andrew Marrufo
	 */
	private class loginHandler implements EventHandler<ActionEvent> {
		@Override
		/**
		 * Handler for ActionEvent
		 *
		 * @param event the given ActionEvent
		 */
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

	/**
	 * handles a user clicking the logout button
	 * 
	 * @author Andrew Marrufo
	 */
	private class logoutHandler implements EventHandler<ActionEvent> {
		@Override
		/**
		 * Handler for ActionEvent
		 *
		 * @param event the given ActionEvent
		 */
		public void handle(ActionEvent event) {
			currUser = null;
			currUserName = null;
			showLoggedOut();
		}
	}

	/**
	 * handles an admin user attempting to add another user
	 * 
	 * @author Andrew Marrufo
	 */
	private class addHandler implements EventHandler<ActionEvent> {
		@Override
		/**	
		 * Handler for ActionEvent
		 *
		 * @param event the given ActionEvent
		 */
		public void handle(ActionEvent event) {
			users.addUser(accountNameField.getText(), passwordField.getText());
			showLoggedInAdmin();
		}
	}

	/**
	 * handles an admin user attempting to remove a user
	 * 
	 * @author Andrew Marrufo
	 */
	private class removeHandler implements EventHandler<ActionEvent> {
		@Override
		/**
		 * Handler for ActionEvent
		 *
		 * @param event the given ActionEvent
		 */
		public void handle(ActionEvent event) {
			users.removeUser(accountNameField.getText());
			showLoggedInAdmin();
		}
	}

	/**
	 * handles when a song ends in the jukebox (recursive, breaks when no song left in queue)
	 * 
	 * @author Andrew Marrufo
	 */
	private class EndOfSongHandler implements Runnable {
		@Override
		public void run() {
			Song nextSong;
			songQueue.nextSong();
			// tries to get next song in queue
			// if there is no song in queue, catches exception
			try {
				nextSong = songQueue.peekNextSong();
			} catch (NoSuchElementException e) {
				nextSong = null;
			}
			songQueueList.getItems().remove(0);
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
	
	/**
	 * ask the user if they would like to save the current state of the jukebox
	 * 
	 * @author derian
	 */
	  private class WritePersistentObject implements EventHandler<WindowEvent> {

		    @Override
		    public void handle(WindowEvent event) {
		      Alert alert = new Alert(AlertType.CONFIRMATION);
		      alert.setTitle("Shut Down Option");
		      alert.setHeaderText("Press ok to save the current jukebox session");
		  //    alert.setContentText("Press cancel while system testing.");
		      Optional<ButtonType> result = alert.showAndWait();

		      if (result.get() == ButtonType.OK) {
		    	  songQueue.writeToFile();
		    	  users.writeToFile();
		      }
		    }
		  }

}