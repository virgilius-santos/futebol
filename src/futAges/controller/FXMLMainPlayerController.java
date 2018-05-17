package futAges.controller;

import futAges.controller.screenFrameWork.ControlledScreen;
import futAges.model.Entity.FrameData;
import futAges.model.Util.StringFuncions;
import futAges.model.Util.Validation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLMainPlayerController implements Initializable, ControlledScreen {

    private DataController dataController;

    @FXML
    private AnchorPane innerPlayerView;
    @FXML
    private FXMLTableViewController innerTableViewController;
    @FXML
    private FXMLPlayerViewController innerPlayerViewController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTableView();
        configureMediaPlayer();
    }

    private void configureTableView() {

        innerTableViewController.setDataSource(new FXMLTableViewController.DataSource() {
            @Override
            public int numberOfItens() {
                return dataController.getDataSize();
            }

            @Override
            public FrameData getFrameData(Integer index) {
                return dataController.getData(index);
            }

        });

        innerTableViewController.setDataListener(new FXMLTableViewController.DataListener() {
            @Override
            public void update(Integer index, String nome) {
                dataController.getData(index).setName(nome);
            }

            @Override
            public void update(Integer index, Integer tempo, String quadrante) {
                Integer q = (quadrante == null || quadrante.isEmpty()) ? null : Integer.parseInt(quadrante);
                dataController.getData(index).setQuadrant(tempo, q);
            }
        });

    }

    private void configureMediaPlayer() {
        innerPlayerViewController.setMediaPlayerDataSource(new FXMLPlayerViewController.PlayerDataSource() {
            @Override
            public void didStepUpdated(String step) {
                Integer tempo = StringFuncions.stringToInt(step);
                dataController.setTempoDivisao(tempo);
            }

            @Override
            public String getCurrentStep() {
                return dataController.getTempoDivisao().toString();
            }

            @Override
            public void didUpdateDuration(Duration newValue) {
                innerTableViewController.updateCurrentTime(Validation.getSeconds(newValue));
            }

            @Override
            public String getFilePath() {
                return dataController.getVideoPath();
            }
        });
    }

    @Override
    public void setDataController(DataController dataController) {
        this.dataController = dataController;

        innerPlayerViewController.loadMedia();
        innerTableViewController.loadFrames();

        innerPlayerView.getScene().setOnKeyPressed( e -> {
            if (e.getCode() == KeyCode.ENTER) {
                int index = dataController.addData(new FrameData());
                if (index != -1) innerTableViewController.insert(index);
            }
        });
    }

    @Override
    public void screenDidDisappear() {
        innerPlayerViewController.closePlayer();
        innerTableViewController.cleanTable();
    }





}
