package sample;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class Controller implements Initializable {
    @FXML
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    private String filePath;

    @FXML
    private Slider slider;

    @FXML
    private HBox menuBar;

    @FXML
    private Slider seekSlider;


    @FXML
    private void handleButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("select your media(*.mp4)", new String[]{"*.mp4"});
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        this.filePath = file.toURI().toString();


        if (this.filePath != null) {
            Media media = new Media(this.filePath);
            this.mediaPlayer = new MediaPlayer(media);
            this.mediaView.setMediaPlayer(this.mediaPlayer);
            DoubleProperty largura = this.mediaView.fitWidthProperty();
            DoubleProperty comprimento = this.mediaView.fitHeightProperty();

            largura.bind(Bindings.selectDouble(this.mediaView.sceneProperty(), "largura"));
            comprimento.bind(Bindings.selectDouble(this.mediaView.sceneProperty(), "comprimeiro"));

            slider.setValue(mediaPlayer.getVolume() * 100);
            slider.valueProperty().addListener(observable -> mediaPlayer.setVolume(mediaPlayer.getVolume() / 100));

            this.mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> seekSlider.setValue(newValue.toSeconds()));

            seekSlider.setOnMouseClicked(event1 -> mediaPlayer.seek(Duration.seconds(seekSlider.getValue())));


        }
        mediaPlayer.play();
    }

    @FXML
    private void pauseVideo(ActionEvent event) {
        mediaPlayer.pause();
    }

    @FXML
    private void playVideo(ActionEvent event) {
        mediaPlayer.play();

    }

    @FXML
    private void skipBackwardVideo(ActionEvent event) {


    }

    @FXML
    private void skipFowardVideo(ActionEvent event) {


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    private void salvarTrabalho(ActionEvent event) {
        //caminho para salvar o video ;

        Alert alert = new Alert(AlertType.CONFIRMATION, "Salvar o arquivo", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            // Salvar o arquivo em tal lugar
        }


    }

}

