package Media;


import Util.StringFuncions;
import javafx.application.Platform;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MediaController {

    private MediaControllerInterface mcInterface;

    private MediaPlayer mediaPlayer;
    private Duration duration;
    private final boolean repeat = false;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;


    public MediaController(String filePath, MediaControllerInterface mcInterface) {
        if (filePath == null || mcInterface == null) return;

        this.mcInterface = mcInterface;

        Media media = new Media(filePath);
        if (media == null) return;


        mediaPlayer = new MediaPlayer(media);
        mcInterface.getMediaView().setMediaPlayer(mediaPlayer);
        configureMediaPlayer();
        configureSlider();

    }

    private void configureMediaPlayer() {
        mediaPlayer.currentTimeProperty().addListener(ov -> updateValues());

        mediaPlayer.setOnPlaying(() -> {
            if (stopRequested) {
                mediaPlayer.pause();
                stopRequested = false;
            }
        });

        mediaPlayer.setOnPaused(() -> {
            System.out.println("onPaused");
            //updateValues();
        });

        mediaPlayer.setOnReady(() -> {
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
    }

    private  void configureSlider() {
        if (this.mcInterface.getSlider() == null) return;

        this.mcInterface.getSlider().setMax(100);

        this.mcInterface.getSlider().valueProperty().addListener(ov -> {
            if (mcInterface.getSlider().isValueChanging()) {
                // multiply duration by percentage calculated by slider position
                mediaPlayer.seek(duration.multiply(mcInterface.getSlider().getValue() / 100.0));
            }
        });

        this.mcInterface.getSlider().setOnMousePressed( event -> {
            mcInterface.getSlider().setValueChanging(true);
            mediaPlayer.pause();
            mediaPlayer.seek(duration.multiply(((Slider)event.getSource()).getValue() / 100.0));
            mcInterface.getSlider().setValueChanging(false);
        });

    }

    private void updateValues() {
        if (mcInterface.getSlider() == null || duration == null) return;

        Platform.runLater(() -> {
            Duration currentTime = mediaPlayer.getCurrentTime();
            updateTimeStamp();
            mcInterface.getSlider().setDisable(duration.isUnknown());
            if (!mcInterface.getSlider().isDisabled()
                    && duration.greaterThan(Duration.ZERO)
                    && !mcInterface.getSlider().isValueChanging()) {
                mcInterface.getSlider().setValue( currentTime.toMillis() / duration.toMillis() * 100.0);
            }
        });

    }

    public Integer getCurrentTime() {
        return (mediaPlayer == null) ? ((Double)mediaPlayer.getCurrentTime().toSeconds()).intValue() : null;
    }

    public void playPause(){
        if (mediaPlayer == null || duration == null) return;

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
            if (atEndOfMedia) {
                mediaPlayer.seek(mediaPlayer.getStartTime());
                atEndOfMedia = false;
            }

            mediaPlayer.play();

        } else {

            mediaPlayer.pause();

        }
    }

    public void skip(Integer step, Boolean backward){
        if (mediaPlayer == null || mcInterface.getSlider() == null) return;
        Integer newStep = (backward) ? -step : step;
        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(newStep)));
        mcInterface.getSlider().setValue(mcInterface.getSlider().getValue() + newStep);
        mediaPlayer.pause();
    }

    private void updateTimeStamp(){
        if (mediaPlayer == null || mcInterface.getLabel() == null) return;
        String formatted = StringFuncions.formatTime(mediaPlayer.getCurrentTime(), duration);
        mcInterface.getLabel().setText(formatted);
    }



}
