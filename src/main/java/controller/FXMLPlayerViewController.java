package controller;

import model.util.Conversion;
import model.util.Validation;
import javafx.application.Platform;
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

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLPlayerViewController implements Initializable {

    public interface PlayerDataSource {
        String getFilePath();
        String getCurrentStep();
        void didStepUpdated(String step);
        void didUpdateDuration(Duration newValue);
    }

    private PlayerDataSource dataSource;
    private MediaPlayer mediaPlayer;
    private Duration duration;
    private boolean repeat;
    private boolean stopRequested;
    private boolean atEndOfMedia;
    private boolean playing;


    @FXML
    private MediaView mediaView;
    @FXML
    private Slider seekSlider;
    @FXML
    private TextField step;
    @FXML
    private Button btnSkipBackward;
    @FXML
    private Button btnSkipForward;
    @FXML
    private Button btnPlayPause;
    @FXML
    private Label labelTime;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureStep();
    }

    private void configureStep() {

        seekSlider.valueProperty().addListener(ov -> {
            if (seekSlider.isValueChanging()) {
                // multiply duration by percentage calculated by slider position
                double newValue = seekSlider.getValue() / seekSlider.getMax();
                mediaPlayer.seek(duration.multiply(newValue));
                updateValues();
            }
        });

        step.textProperty().addListener( (obs, o, n) ->
                didStepUpdated(n)
        );


    }

    private void configureMediaPlayer() {

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

        mediaPlayer.currentTimeProperty().addListener(v -> updateValues());

        mediaView.setMediaPlayer(mediaPlayer);
    }

    @FXML
    void handleSteBackWard() {
        int set = Conversion.stringToInt(step.getText());
        skip(set,true);
    }

    @FXML
    private void handlePlayPause() {
        playPause();
    }

    @FXML
    void handleSteForWard() {
        int set = Conversion.stringToInt(step.getText());
        skip(set,false);
    }

    @FXML
    private void handleOnMouseClicked() {
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
        if(playing) {
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
        Validation.onKeyTyped(event, 6);
    }


    private void skip(Integer step, Boolean backward) {
        if (mediaPlayer == null) return;
        Integer newStep = (backward) ? -step : step;
        Duration newDuration = mediaPlayer.getCurrentTime().add(Duration.seconds(newStep));
        if (newDuration.greaterThan(duration)) newDuration = duration;
        if (newDuration.lessThan(Duration.ZERO)) newDuration = Duration.ZERO;
        mediaPlayer.seek(newDuration);
        seekSlider.setValue(newDuration.toSeconds()/(duration.toSeconds())*100);
        btnPlayPause.setText("Play");
        mediaPlayer.pause();
        updateValues();
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

    private void updateValues() {
        if (duration == null) return;

        Platform.runLater(() -> {
            Duration currentTime = mediaPlayer.getCurrentTime();
            updateTimeStamp();
            seekSlider.setDisable(duration.isUnknown());
            if (!seekSlider.isDisabled()
                    && duration.greaterThan(Duration.ZERO)
                    && !seekSlider.isValueChanging()) {
                seekSlider.setValue( currentTime.toMillis() / duration.toMillis() * seekSlider.getMax());
            }
            didUpdateDuration(currentTime);
        });

    }

    private void updateTimeStamp(){
        if (mediaPlayer == null) return;
        String elapsed = Conversion.formatTime(mediaPlayer.getCurrentTime());
        String total = Conversion.formatTime(duration);
        String formatted = String.format("%s/%s",elapsed,total);
        labelTime.setText(formatted);
    }


    void closePlayer() {
        if (mediaPlayer != null) {
            if (mediaPlayer.getStatus() != MediaPlayer.Status.STOPPED) mediaPlayer.stop();
            mediaPlayer.dispose();
        }
        reset();
        disableView(true);
    }

    void loadMedia(){
        Media media = new Media(getFilePath());
        mediaPlayer = new MediaPlayer(media);
        configureMediaPlayer();
        btnPlayPause.setText("Play");
        step.setText(getCurrentStep());
        disableView(false);

    }

    private void disableView(boolean state){
        seekSlider.setDisable(state);
        btnPlayPause.setDisable(state);
        btnSkipBackward.setDisable(state);
        btnSkipForward.setDisable(state);
        step.setDisable(state);
    }

    private void reset() {
        labelTime.setText("--:--:-- / --:--:--");
        step.setText("");
        seekSlider.setValue(0);
        repeat = false;
        stopRequested = false;
        atEndOfMedia = false;
        playing = false;
    }


    // Data source
    void setMediaPlayerDataSource(PlayerDataSource dataSource){
        this.dataSource = dataSource;
    }

    private String getFilePath() {
        if (dataSource == null) return null;
        return dataSource.getFilePath();
    }

    private String getCurrentStep() {
        if (dataSource == null) return null;
        return dataSource.getCurrentStep();
    }

    private void didStepUpdated(String step) {
        if (dataSource == null) return;
        dataSource.didStepUpdated(step);
    }

    private void didUpdateDuration(Duration newValue){
        if (dataSource == null) return;
        dataSource.didUpdateDuration(newValue);
    }
}
