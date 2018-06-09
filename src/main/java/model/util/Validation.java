package model.util;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;

import java.io.File;

public class Validation {

    public static class MediaException extends Exception {}

    private Validation(){}

    public static void onKeyPressed(KeyEvent evt) {
        if (evt.isControlDown() || evt.isMetaDown() || evt.isAltDown()) {
            evt.consume();
        }
    }

    public static void onKeyTyped(KeyEvent evt) {
        TextField textField = (TextField) evt.getSource();
        String c = evt.getCharacter();

        if (textField.getText().length() > 5 || !c.matches("\\d*")) {
            evt.consume();
        }
    }

    public static void isValidateVideo(String uri) throws MediaException {

        try {
            new Media(uri);
        } catch (Exception e) {
            throw new MediaException();
        }
    }
}
