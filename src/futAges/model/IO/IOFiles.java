package futAges.model.IO;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import futAges.model.Util.Csv;

import java.io.*;

public class IOFiles {

    private static Gson gson = new Gson();

    public static <T> void save(File file, T object){
        if(file == null || object == null) return;
        if ( !file.getName().toLowerCase().contains(".json")){
            file = new File(file+".json");
        }
            String jsonString = gson.toJson(object);
            FileWriter arquivo;

            try {

                arquivo = new FileWriter(file);

                arquivo.write(jsonString);

                arquivo.close();

            } catch (IOException i) {
                System.err.println(i.getMessage());
            }


    }



    public static <T> T load(File file, Class<T> type){

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
