package futAges.view;

import futAges.Main;

import java.io.File;

public class FileChooser {

    public static File getVideoPath(){
        javafx.stage.FileChooser video = new javafx.stage.FileChooser();
        javafx.stage.FileChooser.ExtensionFilter filter = new javafx.stage.FileChooser.ExtensionFilter("Select the video(*.mp4)","*.mp4","*.flv");
        video.getExtensionFilters().add(filter);
        File file = video.showOpenDialog(Main.primaryStage);
        return file;
    }

    public static File getSaveFilePath(){
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new javafx.stage.FileChooser.ExtensionFilter("Text Files", "*.json"));
        //new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showSaveDialog(Main.primaryStage);
        return file;
    }

    public static File getLoadFilePath(){
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new javafx.stage.FileChooser.ExtensionFilter("Text Files", "*.json"),
                new javafx.stage.FileChooser.ExtensionFilter("All Files", "*.*"));

        File file = fileChooser.showOpenDialog(Main.primaryStage);

        return file;
    }

}
