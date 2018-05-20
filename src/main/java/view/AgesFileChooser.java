package view;

import main.Main;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class AgesFileChooser {

    public enum FileTypes {
        JSON("Project Files(*.json)", "*.json"),
        VIDEO("Video files(*.mp4)", "*.mp4","*.flv"),
        CSV("CSV Files(*.csv)", "*.csv")
        ;

        private final String description;
        private final List<String> extensions;
        FileTypes(String description, String... extensions) {
            this.description = description;
            this.extensions = Arrays.asList(extensions);
        }

        String getDescription() {
            return description;
        }

        List<String> getExtensions() {
            return extensions;
        }
    }

    public static File chooseFileToOpen(FileTypes fileTypes){
        FileChooser fileChooser = new FileChooser();
        ExtensionFilter filter = new ExtensionFilter(fileTypes.getDescription(), fileTypes.getExtensions());
        fileChooser.getExtensionFilters().add(filter);
        return fileChooser.showOpenDialog(Main.primaryStage);
    }

    public static File chooseFileToSave(FileTypes fileTypes){
        FileChooser fileChooser = new FileChooser();
        ExtensionFilter filter = new ExtensionFilter(fileTypes.getDescription(), fileTypes.getExtensions());
        fileChooser.getExtensionFilters().add(filter);
        return fileChooser.showSaveDialog(Main.primaryStage);
    }


}
