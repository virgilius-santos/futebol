package model.util;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;

public class Validation {

    public static class MediaException extends Exception {
        private static final long serialVersionUID = -8821628573703324193L;
    }

    private Validation(){}

    public static void onKeyPressed(KeyEvent evt) {
        if (evt.isControlDown() || evt.isMetaDown() || evt.isAltDown()) {
            evt.consume();
        }
    }

    public static void onKeyTyped(KeyEvent evt, int length) {
        TextField textField = (TextField) evt.getSource();
        String c = evt.getCharacter();

        if (textField.getText().length() > length - 1 || !c.matches("\\d*")) {
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
