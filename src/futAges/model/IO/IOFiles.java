package futAges.model.IO;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;

public class IOFiles {

    private static final Gson gson = new Gson();

    public static <T> void saveJsonFile(File file, T dados){

        if(file == null || dados == null) return;

        if ( !file.getName().toLowerCase().contains(".json")){
            file = new File(file+".json");
        }

        String json = gson.toJson(dados);

        save(file, json);
    }

    public static void saveCsvFile(File file, String dados){

        if(file == null || dados == null) return;

        if ( !file.getName().toLowerCase().contains(".csv")){
            file = new File(file+".csv");
        }

        save(file, dados);
    }

    private static void save(File file, String dados) {
        FileWriter arquivo;
        try {
            arquivo = new FileWriter(file);
            arquivo.write(dados);
            arquivo.close();
        } catch (IOException i) {
            System.err.println(i.getMessage());
        }
    }


    public static <T> T loadJsonFile(File file, Class<T> type){

        if (file == null) return null;
        T obj = null;

        FileReader arquivo;

        try {

            arquivo = new FileReader(file);

            JsonReader reader = new JsonReader(arquivo);

            obj = gson.fromJson(reader, type);
            arquivo.close();

        } catch (IOException i) {
            System.err.println(i.getMessage());
        }

        return obj;
    }




}
