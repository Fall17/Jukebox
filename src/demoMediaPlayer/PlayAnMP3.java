package demoMediaPlayer;

/**
 * This code will play "LopingSting.mp3" assuming that file is in
 * a folder named songfiles. You were asked to import this folder 
 * containing mp3 files when accepting the team assignment.
 */
import java.io.File;
import java.net.URI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class PlayAnMP3 extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  private int songsPlayed = 1;
  private static MediaPlayer mediaPlayer;
  

  @Override
  public void start(Stage primaryStage) throws Exception {

    String path = "songfiles/LopingSting.mp3";
    File file = new File(path);
    BorderPane all = new BorderPane();
    all.setTop(new Label("Play one song"));

    // Create a complete path to the mp3 file
    URI uri = file.toURI();
    all.setCenter(new Label(uri.toString()));
    Media media = new Media(uri.toString());
    // Play the song
    mediaPlayer = new MediaPlayer(media);
    mediaPlayer.setAutoPlay(true);
    mediaPlayer.play();

    mediaPlayer.setOnEndOfMedia(new EndOfSongHandler());

    Scene scene = new Scene(all, 300, 80);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private class EndOfSongHandler implements Runnable {
    @Override
    public void run() {
      songsPlayed++;
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Message");
      alert.setHeaderText("Song ended, can now play song #" + songsPlayed);
    }
  }
}
