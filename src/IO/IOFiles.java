package IO;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import javafx.stage.FileChooser;
import sample.Main;

import java.io.*;
import java.lang.reflect.Type;

public class IOFiles {

    private static Gson gson = new Gson();

    public static File getVideoPath(){
        FileChooser video = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select the video(*.mp4)","*.mp4","*.avi","*.wmv","*.flv","*.mkv");
        video.getExtensionFilters().add(filter);
        File file = video.showOpenDialog(Main.primaryStage);
        return file;
    }

    public static File getSaveFilePath(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.json"));
                //new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showSaveDialog(Main.primaryStage);
        return file;
    }

    public static File getLoadFilePath(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.json"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));

        File file = fileChooser.showOpenDialog(Main.primaryStage);

        return file;
    }

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
