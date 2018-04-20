package sample;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    private int count = 0;

    @FXML
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;

    @FXML
    private Slider seekSlider;

    @FXML
    private void videoPath(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select the video(*.mp4)","*.mp4");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        String filePath = file.toURI().toString();

        if (filePath != null) {
            Media media = new Media(filePath);
            this.mediaPlayer = new MediaPlayer(media);
            this.mediaView.setMediaPlayer(this.mediaPlayer);

            // Double time = mediaPlayer.getTotalDuration().toSeconds(); // Pega a duração do vídeo
            // seekSlider.setMax(time); // Define a duração do Slider como a mesma do vídeo

            this.mediaPlayer.currentTimeProperty().addListener((ObservableValue<? extends Duration> observable,Duration oldValue,Duration newValue) -> {
                seekSlider.setValue(newValue.toSeconds());
            });
            seekSlider.setOnMouseClicked(mouseEvent -> {
                mediaPlayer.seek(Duration.seconds(seekSlider.getValue()));
            });
        }

    }

    @FXML
    private void playPause(ActionEvent event) {

        if(count%2 == 0) {
            mediaPlayer.play();
            count++;
        } else {
            mediaPlayer.pause();
            count--;
        }

    }


    @FXML
    private void saveAs(ActionEvent event) { //caminho para salvar o video ;

        Alert alert = new Alert(AlertType.CONFIRMATION, "Save Project", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) { // Salvar o arquivo em tal lugar

            // codar

        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}

