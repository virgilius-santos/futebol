package sample;

import Main.MainController;

import IO.IOFiles;
import Media.MediaController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {

    @FXML
    private Button newProjectButton;
    @FXML
    private Button loadProjectButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        try {
            newProjectButton.setOnAction(evt -> {
                String filePath;
                File file = IOFiles.getVideoPath();

                if( file == null){ return; }
                    filePath = file.toURI().toString();
                    MainController.shared.setVideoPath(filePath);
                    openSecondaryScene();
            });


            loadProjectButton.setOnAction( evt -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Load Project", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {

                    File file = IOFiles.getLoadFilePath();
                    MainController mainController = IOFiles.load(file, MainController.class);

                    if (mainController != null) {
                        MainController.setMainController(mainController);
                        openSecondaryScene();
                    }

                }
            });
             }catch (Exception e){
                e.getMessage();
            }


    }

    public void openSecondaryScene(){
        Stage root = Main.primaryStage;
        try {
            Parent newParent = FXMLLoader.load(getClass().getResource("secondary.fxml"));
            Scene scene =  new Scene(newParent);
            root.setScene(scene);
            root.centerOnScreen();
            root.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
