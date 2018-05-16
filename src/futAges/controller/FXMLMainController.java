package futAges.controller;

import futAges.controller.screenFrameWork.ControlledScreen;
import futAges.controller.screenFrameWork.Screen;
import futAges.modal.IO.IOFiles;
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
    private Screen selectedScreen;
    private Screen mainPlayerScreen;

    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPlayerScreen = new Screen(Screen.ScreenPath.MainPlayer);
    }

    @FXML
    private void handleMenuItemFileLoad() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Load Project", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.NO) return;

        if (selectedScreen != null) {
            ((AnchorPane) selectedScreen.getParent().getParent()).getChildren().remove(selectedScreen.getParent());
            ((ControlledScreen)selectedScreen.getLoader().getController()).screenDidDisappear();
        }

        File file = IOFiles.getLoadFilePath();

        dataController = IOFiles.load(file, DataController.class);
        if (dataController == null) return;

        selectedScreen = mainPlayerScreen;
        anchorPane.getChildren().addAll(mainPlayerScreen.getParent());
        ControlledScreen mainPlayerController = mainPlayerScreen.getLoader().getController();
        mainPlayerController.setDataController(dataController);
    }

    @FXML
    private void handleMenuItemFileSaveAs() throws IOException {
        if (dataController == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Save Project", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.NO) return;

        File file = IOFiles.getSaveFilePath();

        dataController.setProjetoPath(file);
        IOFiles.save(file, dataController);
    }

    @FXML
    private void handleMenuItemFileSave() throws IOException {
        if (dataController == null) return;
        if (dataController.getProjetoPath() == null) handleMenuItemFileSaveAs();

        File file = new File(dataController.getProjetoPath());
        IOFiles.save(file, dataController);
    }

    @FXML
    private void handleMenuItemFileNew() throws IOException {

        dataController = new DataController();

        String filePath;
        File file = IOFiles.getVideoPath();
        if (file == null) return;

        filePath = file.toURI().toString();
        dataController.setVideoPath(filePath);

        if (selectedScreen != null) {
            ((AnchorPane) selectedScreen.getParent().getParent()).getChildren().remove(selectedScreen.getParent());
            ((ControlledScreen)selectedScreen.getLoader().getController()).screenDidDisappear();
        }

        selectedScreen = mainPlayerScreen;
        anchorPane.getChildren().addAll(mainPlayerScreen.getParent());
        ControlledScreen mainPlayerController = mainPlayerScreen.getLoader().getController();
        mainPlayerController.setDataController(dataController);
    }


}
