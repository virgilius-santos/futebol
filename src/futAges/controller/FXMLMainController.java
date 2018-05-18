package futAges.controller;

import futAges.view.AgesFileChooser.FileTypes;
import futAges.controller.screenFrameWork.ControlledScreen;
import futAges.model.IO.IOFiles;
import futAges.model.Util.Csv;
import futAges.view.AgesFileChooser;
import futAges.model.Util.MD5;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

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

        File file = AgesFileChooser.chooseFileToOpen(FileTypes.JSON);
        dataController = IOFiles.loadJsonFile(file, DataController.class);
        if (dataController == null) return;

        String filename = file.getName();
        try {
            if (dataController.getVideoMD5() != MD5.getMD5Checksum(filename)) return;
        } catch(Exception e) {
            e.printStackTrace();
        }

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

        File file = AgesFileChooser.chooseFileToSave(FileTypes.JSON);

        dataController.setProjetoPath(file);
        IOFiles.saveJsonFile(file, dataController);
    }

    @FXML
    private void handleMenuItemFileSave() throws IOException {
        if (dataController == null) return;
        if (dataController.getProjetoPath() == null){
            handleMenuItemFileSaveAs();
            return;
        }

        File file = new File(dataController.getProjetoPath());
        IOFiles.saveJsonFile(file, dataController);
    }

    @FXML
    private void handleMenuItemFileNew() throws IOException {

        dataController = new DataController();

        String filePath;
        File file = AgesFileChooser.chooseFileToOpen(FileTypes.VIDEO);
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

    @FXML
    private void handleMenuItemFileExport() throws IOException {
        if (dataController == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Export Project", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.NO) return;

        File file = AgesFileChooser.chooseFileToSave(FileTypes.CSV);
        if (file == null) return;

        String csv = Csv.converter(dataController.getDados());

        IOFiles.saveCsvFile(file, csv);
    }

}
