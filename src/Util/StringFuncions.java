package Util;

import javafx.util.Duration;

public class StringFuncions {

    public static String formatTime(Duration elapsed, Duration duration){

        int elapsedSeconds = ((int) elapsed.toSeconds()) % 60;
        int elapsedMinutes = ((int) elapsed.toMinutes()) % 60;
        int elapsedHours = (int)  elapsed.toHours();
        int durationSeconds = ((int) duration.toSeconds()) % 60;
        int durationMinutes = ((int) duration.toMinutes()) % 60;
        int durationHours = (int) duration.toHours();

        //String currentTime = String.format("%02d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
        //String totalTime = String.format("%02d:%02d:%02d",durationHours, durationMinutes, durationSeconds) ;


        if (duration.greaterThan(Duration.ZERO)) {

            if (durationHours > 0) {
                return String.format("%02d:%02d:%02d/%02d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds,
                        durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d",
                        elapsedMinutes, elapsedSeconds,durationMinutes,
                        durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%02d:%02d:%02d", elapsedHours,
                        elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d",elapsedMinutes,
                        elapsedSeconds);
            }
        }
    }
}
