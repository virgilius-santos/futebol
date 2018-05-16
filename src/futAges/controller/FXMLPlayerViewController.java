package futAges.controller;

import futAges.model.Util.StringFuncions;
import futAges.model.Util.Validation;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLPlayerViewController implements Initializable {

    private MediaPlayer mediaPlayer;
    private Duration duration;
    private final boolean repeat = false;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private boolean playing = false;

    @FXML
    private MediaView mediaView;
    @FXML
    private Slider seekSlider;
    @FXML
    private TextField step;
    @FXML
    private Button btnPlayPause;
    @FXML
    private Label labelTime;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        seekSlider.setDisable(true);
        seekSlider.valueProperty().addListener(ov -> {
            if (seekSlider.isValueChanging()) {
                // multiply duration by percentage calculated by slider position
                double newValue = seekSlider.getValue() / seekSlider.getMax();
                mediaPlayer.seek(duration.multiply(newValue));
                updateValues();
            }
        });

    }

    @FXML
    private void handleSteBackWard() throws IOException {
        int set = StringFuncions.stringToInt(step.getText());
        skip(set,true);
    }

    @FXML
    private void handlePlayPause() throws IOException {
        playPause();
    }

    @FXML
    private void handleSteForWard() throws IOException {
        int set = StringFuncions.stringToInt(step.getText());
        skip(set,false);
    }

    @FXML
    private void onMouseClicked() {
        updateValues();
    }

    @FXML
    private void sliderOnMousePressed() {
        seekSlider.setValueChanging(true);
        if (!(mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED
                || mediaPlayer.getStatus() == MediaPlayer.Status.READY
                || mediaPlayer.getStatus() == MediaPlayer.Status.STOPPED)) {
            mediaPlayer.pause();
            playing = true;
        }
    }

    @FXML
    private void sliderOnMouseReleased() {
        if(playing == true) {
            mediaPlayer.play();
            playing = false;
        }
        double newValue = seekSlider.getValue() / seekSlider.getMax();
        mediaPlayer.seek(duration.multiply(newValue));
        seekSlider.setValueChanging(false);
    }

    @FXML
    private void textFieldOnKeyPressed(KeyEvent event) {
        Validation.onKeyPressed(event);
    }

    @FXML
    private void textFieldKeyTyped(KeyEvent event) {
        Validation.onKeyTyped(event);
    }

    private void playPause(){

        MediaPlayer.Status status = mediaPlayer.getStatus();

        if (status == MediaPlayer.Status.UNKNOWN  || status == MediaPlayer.Status.HALTED) {
            // don't do anything in these states
            return;
        }

        Double timeRemaing = duration.subtract(mediaPlayer.getCurrentTime()).toSeconds();
        if ( status == MediaPlayer.Status.PAUSED
                || status == MediaPlayer.Status.READY
                || status == MediaPlayer.Status.STOPPED
                || timeRemaing < 1.0) {

            // rewind the movie if we're sitting at the end
            if (atEndOfMedia || timeRemaing < 1.0) {
                mediaPlayer.seek(mediaPlayer.getStartTime());
                atEndOfMedia = false;
            }
            btnPlayPause.setText("Pause");
            mediaPlayer.play();

        } else {
            btnPlayPause.setText("Play");
            mediaPlayer.pause();
        }
    }

    private void updateValues() { ;
        if (duration == null) return;

        Platform.runLater(() -> {
            Duration currentTime = mediaPlayer.getCurrentTime();
            updateTimeStamp();
            seekSlider.setDisable(duration.isUnknown());
            if (!seekSlider.isDisabled()
                    && duration.greaterThan(Duration.ZERO)
                    && !seekSlider.isValueChanging()) {
                seekSlider.setValue( currentTime.toMillis() / duration.toMillis() * 100.0);
            }
        });

    }

    private void updateTimeStamp(){
        if (mediaPlayer == null) return;
        String elapsed = StringFuncions.formatTime(mediaPlayer.getCurrentTime());
        String total = StringFuncions.formatTime(duration);
        String formatted = String.format("%s/%s",elapsed,total);
        labelTime.setText(formatted);
    }

    private void skip(Integer step, Boolean backward){
        if (mediaPlayer == null || seekSlider == null) return;
        Integer newStep = (backward) ? -step : step;
        Duration newDuration = mediaPlayer.getCurrentTime().add(Duration.seconds(newStep));
        if (newDuration.greaterThan(duration)) newDuration = duration;
        if (newDuration.lessThan(Duration.ZERO)) newDuration = Duration.ZERO;
        mediaPlayer.seek(newDuration);
        seekSlider.setValue(newDuration.toSeconds()/(duration.toSeconds())*100);
        mediaPlayer.pause();
    }

    public void setStepListener(ChangeListener<String> listener){
        step.textProperty().addListener(listener);
    }

    public void setStep(Integer step) {
        this.step.setText(String.valueOf(step));
    }

    public void setMediaPlayer(String filePath){
        if (filePath == null) return;

        Media media = new Media(filePath);
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setOnPlaying(() -> {
            if (stopRequested) {
                mediaPlayer.pause();
                stopRequested = false;
            }
        });

        mediaPlayer.setOnReady(() -> {
            seekSlider.setDisable(false);
            duration = mediaPlayer.getMedia().getDuration();
            updateValues();
        });

        mediaPlayer.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);

        mediaPlayer.setOnEndOfMedia(() -> {
            if (!repeat) {
                stopRequested = true;
                atEndOfMedia = true;
            }
        });

        mediaView.setMediaPlayer(mediaPlayer);
    }

    public void setMediaPlayerListener(ChangeListener<Duration> listener){
        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            updateValues();
            listener.changed(observable, oldValue, newValue);
        });
    }

    public void closePlayer() {
        if (mediaPlayer == null) return;
        if (mediaPlayer.getStatus() != MediaPlayer.Status.STOPPED) mediaPlayer.stop();
        mediaPlayer.dispose();
    }
}
