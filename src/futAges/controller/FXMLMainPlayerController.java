package futAges.controller;

import Main.DataController;
import futAges.modal.Util.StringFuncions;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLMainPlayerController implements Initializable {

    private DataController controller;

    @FXML
    private AnchorPane anchorTable;
    @FXML
    private AnchorPane anchorMediaPlayer;

    private FXMLTableController tableController;
    private FXMLPlayerViewController playerViewController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            initializeTable();
            initializePlayer();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void initializeTable() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        String path = "/futAges/view/TableView.fxml";
        loader.setLocation(FXMLTableController.class.getResource(path));
        AnchorPane a = loader.load();

        tableController = loader.getController();

        anchorTable.getChildren().add(a);
        AnchorPane.setTopAnchor(a,0.0);
        AnchorPane.setBottomAnchor(a,0.0);
        AnchorPane.setLeftAnchor(a,0.0);
        AnchorPane.setRightAnchor(a,0.0);
    }

    public void initializePlayer() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        String path = "/futAges/view/PlayerView.fxml";
        loader.setLocation(FXMLPlayerViewController.class.getResource(path));
        AnchorPane a = loader.load();

        playerViewController = loader.getController();

        anchorMediaPlayer.getChildren().add(a);
        AnchorPane.setTopAnchor(a,0.0);
        AnchorPane.setBottomAnchor(a,0.0);
        AnchorPane.setLeftAnchor(a,0.0);
        AnchorPane.setRightAnchor(a,0.0);

    }

    public void setController(DataController controller) {
        this.controller = controller;
        configureMediaPlayerStep();
        configureMediaPlayer();
    }

    private void configureMediaPlayerStep() {
        playerViewController.setStep(controller.getTempoDivisao());
        playerViewController.setStepListener((observable, oldValue, newValue) -> {
            int value = StringFuncions.stringToInt(newValue);
            controller.setTempo(value);
        });
    }

    private void configureMediaPlayer() {
        playerViewController.closePlayer();
        playerViewController.setMediaPlayer(controller.getVideoPath());
        playerViewController.setMediaPlayerListener((observable, oldValue, newValue) -> {
        });
    }
}
