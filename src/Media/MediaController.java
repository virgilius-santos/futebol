package Media;


import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class MediaController {

    private MediaPlayer mediaPlayer;
    private Duration duration;
    private final boolean repeat = false;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private Label timeStamp;

    private Slider seekSlider;

    public MediaController(String filePath, MediaView mediaView) {
        if (filePath == null || mediaView == null) return;

        Media media = new Media(filePath);
        this.mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(this.mediaPlayer);

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

    public void setTimeStamp(Label timeStamp) {
        this.timeStamp = timeStamp;
    }

    private void updateValues() {
        if (seekSlider != null) {
            Platform.runLater(() -> {
                Duration currentTime = mediaPlayer.getCurrentTime();
                updateTimeStamp(timeStamp);
                seekSlider.setDisable(duration.isUnknown());
                if (!seekSlider.isDisabled()
                        && duration.greaterThan(Duration.ZERO)
                        && !seekSlider.isValueChanging()) {
                    seekSlider.setValue( currentTime.toMillis() / duration.toMillis() * 100.0);
                }
            });
        }
    }

    public void setSeekSlider(Slider seekSlider) {
        this.seekSlider = seekSlider;
        this.seekSlider.setMax(100);
        this.seekSlider.valueProperty().addListener(ov -> {
            if (seekSlider.isValueChanging()) {
                // multiply duration by percentage calculated by slider position
                mediaPlayer.seek(duration.multiply(seekSlider.getValue() / 100.0));
            }
        });
        this.seekSlider.setOnMousePressed( event -> {
//            mouseClicked = true;
            seekSlider.setValueChanging(true);
            mediaPlayer.pause();
            mediaPlayer.seek(duration.multiply(((Slider)event.getSource()).getValue() / 100.0));
            //seekSlider.setValue( ((Slider)event.getSource()).getValue());

            seekSlider.setValueChanging(false);
        });

    }

    public Integer getCurrentTime() {
        return ((Double)mediaPlayer.getCurrentTime().toSeconds()).intValue();
    }

    public void playPause(){
        MediaPlayer.Status status = mediaPlayer.getStatus();

        if (status == MediaPlayer.Status.UNKNOWN  || status == MediaPlayer.Status.HALTED)
        {
            // don't do anything in these states
            return;
        }
        Double timeRemaing = duration.subtract(mediaPlayer.getCurrentTime()).toSeconds();
        if ( status == MediaPlayer.Status.PAUSED
                || status == MediaPlayer.Status.READY
                || status == MediaPlayer.Status.STOPPED
                || timeRemaing < 1.0)
        {
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
        Integer newStep = (backward) ? -step : step;
        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(newStep)));
        seekSlider.setValue(seekSlider.getValue() + newStep);
        mediaPlayer.pause();
    }

    private void updateTimeStamp(Label timeStamp){
        if (timeStamp != null) {
            timeStamp.setText(formatTime(mediaPlayer.getCurrentTime(), duration));
        }
    }

    private static String formatTime(Duration elapsed, Duration duration){
        int intElapsed = (int)Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
                - elapsedMinutes * 60;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int)Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60 -
                    durationMinutes * 60;
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d/%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds,
                        durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d",
                        elapsedMinutes, elapsedSeconds,durationMinutes,
                        durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours,
                        elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d",elapsedMinutes,
                        elapsedSeconds);
            }
        }
    }

}
