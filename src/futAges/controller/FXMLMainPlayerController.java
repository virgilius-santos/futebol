package futAges.controller;

import futAges.controller.screenFrameWork.ControlledScreen;
import futAges.controller.screenFrameWork.Screen;
import futAges.modal.Util.StringFuncions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLMainPlayerController implements Initializable, ControlledScreen {

    private DataController controller;

    private Screen tableView;
    private Screen playerView;

    private FXMLTableViewController tableController;
    private FXMLPlayerViewController playerViewController;

    @FXML
    private AnchorPane anchorTable;
    @FXML
    private AnchorPane anchorMediaPlayer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTableView();
        initializePlayerView();
    }

    public void initializeTableView() {
        tableView = new Screen(Screen.ScreenPath.TableView);
        tableController = tableView.getLoader().getController();
        initialize(tableView, anchorTable);
    }

    public void initializePlayerView() {
        playerView = new Screen(Screen.ScreenPath.PlayerView);
        playerViewController = playerView.getLoader().getController();

        playerViewController.setStepListener((observable, oldValue, newValue) -> {
            int value = StringFuncions.stringToInt(newValue);
            controller.setTempo(value);
        });

        playerViewController.setMediaPlayerListener((observable, oldValue, newValue) -> {
            // TODO
        });

        initialize(playerView, anchorMediaPlayer);
    }

    public void initialize(Screen screen, AnchorPane anchor) {

        AnchorPane a = (AnchorPane) screen.getParent();

        anchor.getChildren().add(a);
        AnchorPane.setTopAnchor(a,0.0);
        AnchorPane.setBottomAnchor(a,0.0);
        AnchorPane.setLeftAnchor(a,0.0);
        AnchorPane.setRightAnchor(a,0.0);
    }

    @Override
    public void setDataController(DataController dataController) {
        controller = dataController;
        configureMediaPlayerStep();
        configureMediaPlayer();
    }

    @Override
    public void screenDidDisappear() {
        playerViewController.closePlayer();
    }

    private void configureMediaPlayerStep() {
        playerViewController.setStep(controller.getTempoDivisao());
    }

    private void configureMediaPlayer() {
        playerViewController.setMediaPlayer(controller.getVideoPath());
    }
}
