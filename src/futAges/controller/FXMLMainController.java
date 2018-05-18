package futAges.controller;

import futAges.controller.screenFrameWork.ControlledScreen;
import futAges.model.IO.IOFiles;
import futAges.model.Util.MD5;
import futAges.view.FileChooser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLMainController implements Initializable {

    private DataController dataController;
    private ControlledScreen selectedController;

    @FXML
    private FXMLMainPlayerController innerMainPlayerViewController;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void handleMenuItemFileLoad() throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Load Project", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.NO) return;

        File file = FileChooser.getLoadFilePath();
        dataController = IOFiles.load(file, DataController.class);
        if (dataController == null) return;

        if (selectedController != null) innerMainPlayerViewController.screenDidDisappear();

        selectedController = innerMainPlayerViewController;
        selectedController.setDataController(dataController);
    }

    @FXML
    private void handleMenuItemFileSaveAs() throws IOException {
        if (dataController == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Save Project", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.NO) return;

        File file = FileChooser.getSaveFilePath();

        dataController.setProjetoPath(file);
        IOFiles.save(file, dataController);
    }

    @FXML
    private void handleMenuItemFileSave() throws IOException {
        if (dataController == null) return;
        if (dataController.getProjetoPath() == null){
            handleMenuItemFileSaveAs();
            return;
        }

        File file = new File(dataController.getProjetoPath());
        IOFiles.save(file, dataController);
    }

    @FXML
    private void handleMenuItemFileNew() throws IOException {

        dataController = new DataController();

        String filePath;
        File file = FileChooser.getVideoPath();
        if (file == null) return;

        filePath = file.toURI().toString();

        String filename = file.getName();
        try {
            dataController.setVideoMD5(MD5.getMD5Checksum(filename));
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        dataController.setVideoPath(filePath);

        if (selectedController != null) innerMainPlayerViewController.screenDidDisappear();

        selectedController = innerMainPlayerViewController;
        selectedController.setDataController(dataController);
    }


}
