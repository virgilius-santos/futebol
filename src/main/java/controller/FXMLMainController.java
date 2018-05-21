package controller;

import model.Entity.ProjectData;
import controller.screenFrameWork.ControlledScreen;
import model.IO.IOFiles;
import model.Util.Conversion;
import view.AgesFileChooser;
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
    private void handleMenuItemFileClose() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Close Project", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.NO) return;
            if(projectData == null) {
                System.exit(0);
            }
            else {
                String msg = "Você irá perder todas as informações não salvas, \nDeseja prosseguir com o fechamento do projeto?";
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, msg, ButtonType.YES, ButtonType.NO);
                alert2.showAndWait();
                if(alert2.getResult() == ButtonType.YES) {
                    selectedController.screenDidDisappear();
                    projectData = null;
                }else return;
            }
    }

    @FXML
    private void handleMenuItemFileLoad() throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Load Project", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.NO) return;

        File file = AgesFileChooser.chooseFileToOpen(AgesFileChooser.FileTypes.JSON);
        projectData = IOFiles.loadJsonFile(file, ProjectData.class);
        if (projectData == null) return;

        try {
            String md5 = Conversion.getMD5Checksum(projectData.getVideoFile());
            if (!projectData.getVideoMD5().equals(md5)) return;
        } catch(Exception e) {
            e.printStackTrace();
            return;
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

        File file = AgesFileChooser.chooseFileToSave(AgesFileChooser.FileTypes.JSON);

        projectData.setProjetoFile(file);
        IOFiles.saveJsonFile(file, projectData);
    }

    @FXML
    private void handleMenuItemFileSave() throws IOException {
        if (projectData == null) return;
        if (projectData.getProjetoFile() == null){
            handleMenuItemFileSaveAs();
            return;
        }

        IOFiles.saveJsonFile(projectData.getProjetoFile(), projectData);
    }

    @FXML
    private void handleMenuItemFileNew() throws IOException {

        File file = AgesFileChooser.chooseFileToOpen(AgesFileChooser.FileTypes.VIDEO);
        if (file == null) return;

        String md5;
        try {
            md5 = Conversion.getMD5Checksum(file);
        } catch(Exception e) {
            e.printStackTrace();
            return;
        }

        projectData = new ProjectData(file);
        projectData.setVideoMD5(md5);

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

        File file = AgesFileChooser.chooseFileToSave(AgesFileChooser.FileTypes.CSV);
        if (file == null) return;

        String csv = Conversion.converter(projectData.getDados());

        IOFiles.saveCsvFile(file, csv);
    }

}
