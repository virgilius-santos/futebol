package model.util;

import javafx.util.Duration;
import model.entity.FrameData;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @org.junit.Test
    public void getMD5Checksum() throws Exception {
        File video = new File("VideoA.mp4");
        String md5 = "a0e9d74b6ed985e3cfaa42df337d7f36";
        assertEquals(md5, Conversion.getMD5Checksum(video));
    }

    @org.junit.Test
    public void converter() throws Exception {
        ArrayList<FrameData> dados = new ArrayList<>(10);
        dados.add(new FrameData());
        dados.add(new FrameData());
        dados.get(0).setName("Bob");
        dados.get(1).setName("Bola");
        dados.get(0).setQuadrant(0,8);
        dados.get(0).setQuadrant(2,10);
        dados.get(1).setQuadrant(0,15);
        dados.get(1).setQuadrant(2,12);
        String csv = "Tempo;Bob;Bola;\n" +
                "0;8;15;\n" +
                "2;10;12;\n";
        assertEquals(csv, Conversion.converter(dados));
    }

}