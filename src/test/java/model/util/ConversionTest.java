package model.util;

import javafx.util.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class ConversionTest {

    @org.junit.Test
    public void stringToInt() throws Exception {
        assertEquals(0, Conversion.stringToInt("abc"));
        assertEquals(0, Conversion.stringToInt(null));
        assertEquals(0, Conversion.stringToInt(""));
        assertEquals(-123, Conversion.stringToInt("-123"));
        assertEquals(0, Conversion.stringToInt("0"));
        assertEquals(123, Conversion.stringToInt("123"));
    }

    @org.junit.Test
    public void getSeconds() throws Exception {
        Duration duration = new Duration(1000);
        assertEquals((Integer) 1, Conversion.getSeconds(duration));
    }

    @org.junit.Test
    public void formatTime() throws Exception {
        
    }

}