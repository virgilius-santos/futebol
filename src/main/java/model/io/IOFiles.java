package model.io;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IOFiles {

    private static final Gson gson = new Gson();

    private IOFiles(){}

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
        try (FileWriter arquivo = new FileWriter(file)) {
            arquivo.write(dados);
        } catch (IOException i) {
            Logger.getGlobal().log(Level.ALL, i.getMessage());
        }
    }


    public static <T> T loadJsonFile(File file, Class<T> type){

        if (file == null) return null;
        T obj = null;

        try (FileReader arquivo = new FileReader(file)) {

            JsonReader reader = new JsonReader(arquivo);
            obj = gson.fromJson(reader, type);

        } catch (IOException i) {
            Logger.getGlobal().log(Level.ALL, i.getMessage());
        }

        return obj;
    }




}
