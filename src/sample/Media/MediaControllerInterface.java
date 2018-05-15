package sample.Media;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaView;

public interface MediaControllerInterface {
    Label getLabel();
    MediaView getMediaView();
    Slider getSlider();
    Button getPlayPause();
    Button getSkipBackWard();
    Button getSkipForWard();
    Integer timeStep();
}
