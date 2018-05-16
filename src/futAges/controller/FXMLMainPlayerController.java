package futAges.controller;

import futAges.controller.screenFrameWork.ControlledScreen;
import futAges.controller.screenFrameWork.Screen;
import futAges.model.Entity.FrameData;
import futAges.model.Util.StringFuncions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FXMLMainPlayerController implements Initializable, ControlledScreen {

    private DataController controller;
    private List<FrameData> values;

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

    private void initializeTableView() {
        Screen tableView = new Screen(Screen.ScreenPath.TableView);
        tableController = tableView.getLoader().getController();
        initialize(tableView, anchorTable);
    }

    private void initializePlayerView() {
        Screen playerView = new Screen(Screen.ScreenPath.PlayerView);
        playerViewController = playerView.getLoader().getController();

        playerViewController.setStepListener((observable, oldValue, newValue) -> {
            int value = StringFuncions.stringToInt(newValue);
            controller.setTempoDivisao(value);
        });

        initialize(playerView, anchorMediaPlayer);
    }

    private void initialize(Screen screen, AnchorPane anchor) {

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
        updateValues();

        configureTableView();

        configureMediaPlayerStep();
        configureMediaPlayer();
    }

    @Override
    public void screenDidDisappear() {
        playerViewController.closePlayer();
    }

    private void configureTableView() {

        tableController.setDataSource(new FXMLTableViewController.DataSource() {
            @Override
            public int numberOfItens() {
                return values.size();
            }

            @Override
            public FrameData getData(Integer index) {
                return values.get(index);
            }

            @Override
            public int createData() {
                int index = values.size();
                controller.addData(new FrameData());
                updateValues();
                return index;
            }
        });

        tableController.setDataListener(new FXMLTableViewController.DataListener() {
            @Override
            public void update(Integer frameId, String nome) {
                controller.getData(frameId).setName(nome);
            }

            @Override
            public void update(Integer frameId, Integer tempo, String quadrante) {
                Integer q = (quadrante == null || quadrante.isEmpty()) ? null : Integer.parseInt(quadrante);
                controller.getData(frameId).setQuadrant(tempo, q);
            }
        });

        tableController.reloadFrames();
    }

    private void configureMediaPlayerStep() {
        playerViewController.setStep(controller.getTempoDivisao());
    }

    private void configureMediaPlayer() {
        playerViewController.setMediaPlayer(controller.getVideoPath());
        playerViewController.setMediaPlayerListener((observable, oldValue, newValue) ->
                tableController.updateCurrentTime( ((Double) newValue.toSeconds()).intValue() )
        );
    }

    private void updateValues(){
        values = controller.getDados()
                .values()
                .stream().sorted(
                        (e1, e2) -> (e1.getId() > e2.getId()) ? 1 : ((e1.getId() < e2.getId()) ? -1 : 0)
                )
                .collect(Collectors.toList());
    }
}
