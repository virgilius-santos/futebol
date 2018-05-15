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

    private Screen selectedScreen;
    private Screen mainPlayerScreen;

    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPlayerScreen = new Screen(Screen.ScreenPath.MainPlayer);
    }

    public void handleMenuItemFileLoad() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Load Project", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.NO) return;

        File file = IOFiles.getLoadFilePath();

        DataController dataController = IOFiles.load(file, DataController.class);
        if (dataController == null) return;

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
