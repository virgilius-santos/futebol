package Media;


import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import java.util.EventListener;

public class MediaController {

    public static MediaController shared = new MediaController();

    private MediaPlayer mediaPlayer;
    private Slider seekSlider;

    private MediaController() {

    }

    public void setPosition(Double newValue){
        mediaPlayer.seek(Duration.seconds(newValue));
    }

    public void setMedia(String filePath, MediaView mediaView){
        if (filePath == null || mediaView == null) return;

        Media media = new Media(filePath);
        this.mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(this.mediaPlayer);
    }

    public void setSeekSlider(Slider seekSlider) {
        this.seekSlider = seekSlider;
        this.seekSlider.setMax(1);

        this.mediaPlayer.currentTimeProperty().addListener((ObservableValue<? extends Duration> observable,
                                                            Duration oldValue,
                                                            Duration newValue) -> {
            seekSlider.setValue(getSeekSliderCurrentValue(newValue));
        });

        seekSlider.setOnMouseClicked(mouseEvent -> {
            mediaPlayer.seek(getMediaPlayerCurrentValue());
        });

        seekSlider.setOnMousePressed(event -> mediaPlayer.pause());
    }


    public void playPause(){
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED
                || mediaPlayer.getStatus() == MediaPlayer.Status.READY) {
            mediaPlayer.play();
        } else if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
        }
    }

    public void skip(Double step, Boolean backward){
        Double newStep = (backward) ? -step : step;
        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(new Duration(newStep)));
        mediaPlayer.pause();
    }

    private Double getSeekSliderCurrentValue(Duration duration){
        return seekSlider.getMax() * duration.toSeconds() / mediaPlayer.getTotalDuration().toSeconds();
    }

    private Duration getMediaPlayerCurrentValue(){
        return Duration.seconds(seekSlider.getValue() * mediaPlayer.getTotalDuration().toSeconds());
    }



}
