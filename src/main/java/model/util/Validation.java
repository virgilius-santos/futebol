package model.util;

import javafx.event.EventType;
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

        String selectedText = textField.getSelectedText();
        String text =  textField.getText();
        String c = evt.getCharacter();

        int loadLength = text.length() - selectedText.length();

        if (loadLength >= length || !c.matches("\\d*")) {
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
