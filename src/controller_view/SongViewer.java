package controller_view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Song;
import model.SongQueue;

/**
 * A TableViewer designed for song selection in JukeBoxStartGUI.
 * Shows the number of times each song has been played, the song title,
 * the artist, and the length of the song.
 * 
 * @author Andrew Marrufo
 */
public class SongViewer extends TableView<Song> {

	/**
	 * Constructor.
	 */
	@SuppressWarnings("unchecked")
	public SongViewer() {
		// Add columns with the column name as an argument.
		// A TableColumn requires the type of object in the list.
		// followed by the type of the instance variable to be displayed.
		TableColumn<Song, Integer> playsColumn = new TableColumn<>("Plays");
		TableColumn<Song, String> titleColumn = new TableColumn<>("Title");
		TableColumn<Song, String> artistColumn = new TableColumn<>("Artist");
		TableColumn<Song, String> timeColumn = new TableColumn<>("Time");

		// We need methods that begin with set and get followed by the instance
		// variable name with an upper case first letter: getName and setName
		// for example.
		playsColumn.setCellValueFactory(new PropertyValueFactory<Song, Integer>("timesPlayed"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("name"));
		artistColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("artist"));
		timeColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("songLengthInMinutes"));

		this.getColumns().addAll(playsColumn, titleColumn, artistColumn, timeColumn);

		playsColumn.setPrefWidth(50);
		titleColumn.setPrefWidth(160);
		artistColumn.setPrefWidth(100);
		timeColumn.setPrefWidth(50);

		// Have this TableView show in 400 pixels, even if the space provided in bigger
		this.setMaxWidth(400);

		// Set up the the model for this TableView
		setObserverListToViewSongs();
	}

	/**
	 * Sets up all available songs in TableView
	 */
	public void setObserverListToViewSongs() {
		SongQueue songList = new SongQueue();
		ObservableList<Song> songs = FXCollections.observableArrayList();
		for (int i = 0; i < songList.getSongList().size(); i++) {
			songs.add(songList.getSongList().get(i));
		}
		this.setItems(songs);
		this.getSelectionModel().select(0);
	}
}
