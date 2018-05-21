package model.Util;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

public class Validation {

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
}
