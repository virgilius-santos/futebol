package model.util;

import javafx.util.Duration;
import model.entity.FrameData;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.TreeMap;

public class Conversion {

    private static final String KEY_TEMPO = "Tempo;";
    private static final String KEY_SEMI_COLON = ";";
    private static final String KEY_BREAK_LINE = "\n";
    private static final String KEY_EMPTY = "";

    private Conversion(){}

    public static String convertToCSV(List<FrameData> dados){
        StringBuilder builder = new StringBuilder();
        TreeMap<Integer, Integer[]> linhas = new TreeMap<>();
        int countObj = dados.size();

        FrameData frameData;
        String nome;

        builder.append(KEY_TEMPO);

        for(int id=0; id<countObj; id++){
            frameData = dados.get(id);
            nome = frameData.getName();
            builder.append(nome).append(KEY_SEMI_COLON);

            int finalId = id;
            frameData.getQuadrants().forEach((tempo,quadrante) -> {
                if (!linhas.containsKey(tempo)) {
                    linhas.put(tempo, new Integer[countObj]);
                }
                linhas.get(tempo)[finalId] = quadrante;
            });

        }
        builder.append(KEY_BREAK_LINE);

        linhas.forEach((key, colunas) -> {

            builder.append(key).append(KEY_SEMI_COLON);

            for (Integer coluna : colunas) {
                builder.append((coluna == null) ? KEY_EMPTY : coluna.toString())
                        .append(KEY_SEMI_COLON);
            }

            builder.append(KEY_BREAK_LINE);

        });

        return builder.toString();
    }

    public static Integer getSeconds(Duration duration){
        return ((Double) duration.toSeconds()).intValue();
    }

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

    private static byte[] createChecksum(File file) throws IOException, NoSuchAlgorithmException {
        MessageDigest complete;
        try (InputStream fis = new FileInputStream(file)) {

            byte[] buffer = new byte[1024];
            complete = MessageDigest.getInstance("MD5");
            int numRead;

            do {
                numRead = fis.read(buffer);
                if (numRead > 0) {
                    complete.update(buffer, 0, numRead);
                }
            } while (numRead != -1);

        }
        return complete.digest();
    }

    public static String getMD5CheckSum(File file) throws IOException, NoSuchAlgorithmException {
        byte[] b = createChecksum(file);
        StringBuilder result = new StringBuilder();

        for (byte aB : b) {
            result.append(Integer.toString((aB & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }
}
