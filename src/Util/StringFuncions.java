package Util;

import javafx.util.Duration;

public class StringFuncions {

    public static String formatTime(Duration elapsed, Duration duration){

        int elapsedSeconds = (int) elapsed.toSeconds();
        int elapsedMinutes = (int) elapsed.toMinutes();
        int elapsedHours = (int)  elapsed.toHours();
        int durationSeconds = (int) duration.toSeconds();
        int durationMinutes = (int) duration.toMinutes();
        int durationHours = (int) duration.toHours();

        //String currentTime = String.format("%02d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
        //String totalTime = String.format("%02d:%02d:%02d",durationHours, durationMinutes, durationSeconds) ;


        if (duration.greaterThan(Duration.ZERO)) {

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
