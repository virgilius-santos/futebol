package main.java.model.Util;

import javafx.util.Duration;

public class StringFuncions {

    public static String formatTime(Duration duration){

        int elapsedSeconds = ((int) duration.toSeconds()) % 60;
        int elapsedMinutes = ((int) duration.toMinutes()) % 60;
        int elapsedHours = (int)  duration.toHours();

        if (elapsedHours > 0) {
            return String.format("%02d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
        } else {
            return String.format("%02d:%02d",elapsedMinutes, elapsedSeconds);
        }

    }

    public static int stringToInt(String value) {

        if (value == null || value.isEmpty()) return 0;

        Integer aux;

        try {
            aux = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }

        return aux;

    }
}
