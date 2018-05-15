package futAges.controller;

import futAges.modal.IO.IOFiles;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLMainController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleMenuItemFileLoad() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Load Project", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.NO) return;

        File file = IOFiles.getLoadFilePath();

        DataController dataController = IOFiles.load(file, DataController.class);
        if (dataController == null) return;

        FXMLLoader loader = new FXMLLoader();
        String path = "/futAges/view/MainPlayer.fxml";
        loader.setLocation(FXMLMainPlayerController.class.getResource(path));
        AnchorPane a = loader.load();

        FXMLMainPlayerController mainPlayerController = loader.getController();
        mainPlayerController.setController(dataController);

        anchorPane.getChildren().add(a);
        AnchorPane.setTopAnchor(a,0.0);
        AnchorPane.setBottomAnchor(a,0.0);
        AnchorPane.setLeftAnchor(a,0.0);
        AnchorPane.setRightAnchor(a,0.0);

    }


}
