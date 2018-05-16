package futAges.view;

import futAges.Main;

import java.io.File;

public class FileChooser {

    public static File getVideoPath(){
        javafx.stage.FileChooser video = new javafx.stage.FileChooser();
        javafx.stage.FileChooser.ExtensionFilter filter = new javafx.stage.FileChooser.ExtensionFilter("Select the video(*.mp4)","*.mp4","*.flv");
        video.getExtensionFilters().add(filter);
        return video.showOpenDialog(Main.primaryStage);
    }

    public static File getSaveFilePath(){
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new javafx.stage.FileChooser.ExtensionFilter("Text Files", "*.json"));
        //new FileChooser.ExtensionFilter("All Files", "*.*"));
        return fileChooser.showSaveDialog(Main.primaryStage);
    }

    public static File getLoadFilePath(){
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new javafx.stage.FileChooser.ExtensionFilter("Text Files", "*.json"),
                new javafx.stage.FileChooser.ExtensionFilter("All Files", "*.*"));

        return fileChooser.showOpenDialog(Main.primaryStage);
    }

}
