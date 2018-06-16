package model.util;

import javafx.util.Duration;
import model.entity.FrameData;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ConversionTest {

    /**
     * Testa se o método converte para um inteiro se a String for um int ou retorna 0,
     * caso não seja.
     */
    @org.junit.Test
    public void stringToInt() {
        assertEquals(0, Conversion.stringToInt("abc"));
        assertEquals(0, Conversion.stringToInt(null));
        assertEquals(0, Conversion.stringToInt(""));
        assertEquals(-123, Conversion.stringToInt("-123"));
        assertEquals(0, Conversion.stringToInt("0"));
        assertEquals(123, Conversion.stringToInt("123"));
    }

    /**
     * Testa se a duração passada por parametro converte de milissegundos para o número correspondente,
     * em segundos.
     */
    @org.junit.Test
    public void getSeconds() {
        Duration duration = new Duration(1000);
        assertEquals((Integer) 1, Conversion.getSeconds(duration));
    }

    /**
     * Testa se o vídeo selecionado converte corretamente na hash MD5
     * @throws Exception
     */
    @org.junit.Test
    public void getMD5Checksum() throws Exception {
        File video = new File("VideoA.mp4");
        String md5 = "a0e9d74b6ed985e3cfaa42df337d7f36";
        assertEquals(md5, Conversion.getMD5CheckSum(video));
    }

    /**
     * Testa a conversão para .csv, tendo os campos de tempo, nome dos objetos,
     * e em seguida o tempo em que os objetos estão, da posição dos quadrantes.
     */
    @org.junit.Test
    public void converter() {
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
        assertEquals(csv, Conversion.convertToCSV(dados));
    }

    /**
     * Testa se a conversão em milissegundos passada está convertendo no formato "HH:MM:SS".
     */
    @org.junit.Test
    public void formatTime() {
        Duration d1 = new Duration(2000);
        Duration d2 = new Duration(3123);
        Duration d3 = new Duration(60000);
        Duration d4 = new Duration(3600000);
        assertEquals("00:02", Conversion.formatTime(d1));
        assertEquals("00:03", Conversion.formatTime(d2));
        assertEquals("01:00", Conversion.formatTime(d3));
        assertEquals("01:00:00", Conversion.formatTime(d4));
    }

}