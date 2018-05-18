package futAges.controller;

import futAges.model.Entity.ProjectData;
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

    private ProjectData projectData;
    private ControlledScreen selectedController;

    @FXML
    private FXMLProjectController innerMainPlayerViewController;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void handleMenuItemFileLoad() throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Load Project", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.NO) return;

        File file = AgesFileChooser.chooseFileToOpen(FileTypes.JSON);
        projectData = IOFiles.loadJsonFile(file, ProjectData.class);
        if (projectData == null) return;

        String filename = file.getName();
        try {
            if (!projectData.getVideoMD5().equals(MD5.getMD5Checksum(filename))) return;
        } catch(Exception e) {
            e.printStackTrace();
        }

        if (selectedController != null) selectedController.screenDidDisappear();

        selectedController = innerMainPlayerViewController;
        selectedController.setProjectData(projectData);
    }

    @FXML
    private void handleMenuItemFileSaveAs() throws IOException {
        if (projectData == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Save Project", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.NO) return;

        File file = AgesFileChooser.chooseFileToSave(FileTypes.JSON);

        projectData.setProjetoPath(file);
        IOFiles.saveJsonFile(file, projectData);
    }

    @FXML
    private void handleMenuItemFileSave() throws IOException {
        if (projectData == null) return;
        if (projectData.getProjetoPath() == null){
            handleMenuItemFileSaveAs();
            return;
        }

        File file = new File(projectData.getProjetoPath());
        IOFiles.saveJsonFile(file, projectData);
    }

    @FXML
    private void handleMenuItemFileNew() throws IOException {

        projectData = new ProjectData();

        String filePath;
        File file = AgesFileChooser.chooseFileToOpen(FileTypes.VIDEO);
        if (file == null) return;

        filePath = file.toURI().toString();

        String filename = file.getName();
        try {
            projectData.setVideoMD5(MD5.getMD5Checksum(filename));
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        projectData.setVideoPath(filePath);

        if (selectedController != null) selectedController.screenDidDisappear();

        selectedController = innerMainPlayerViewController;
        selectedController.setProjectData(projectData);
    }

    @FXML
    private void handleMenuItemFileExport() throws IOException {
        if (projectData == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Export Project", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.NO) return;

        File file = AgesFileChooser.chooseFileToSave(FileTypes.CSV);
        if (file == null) return;

        String csv = Csv.converter(projectData.getDados());

        IOFiles.saveCsvFile(file, csv);
    }

}
