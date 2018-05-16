package futAges.controller;

import futAges.controller.screenFrameWork.ControlledScreen;
import futAges.model.Entity.FrameData;
import futAges.model.Util.StringFuncions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLMainPlayerController implements Initializable, ControlledScreen {

    private DataController dataController;

    @FXML
    private FXMLTableViewController innerTableViewController;
    @FXML
    private FXMLPlayerViewController innerPlayerViewController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    @Override
    public void setDataController(DataController dataController) {
        this.dataController = dataController;
        configureTableView();
        configureMediaPlayer();
    }

    @Override
    public void screenDidDisappear() {
        innerPlayerViewController.closePlayer();
    }

    private void configureTableView() {

        innerTableViewController.setDataSource(new FXMLTableViewController.DataSource() {
            @Override
            public int numberOfItens() {
                return dataController.getDataSize();
            }

            @Override
            public FrameData getData(Integer index) {
                return dataController.getData(index);
            }

            @Override
            public int createData() {
                return dataController.addData(new FrameData());
            }
        });

        innerTableViewController.setDataListener(new FXMLTableViewController.DataListener() {
            @Override
            public void update(Integer frameId, String nome) {
                dataController.getData(frameId).setName(nome);
            }

            @Override
            public void update(Integer frameId, Integer tempo, String quadrante) {
                Integer q = (quadrante == null || quadrante.isEmpty()) ? null : Integer.parseInt(quadrante);
                dataController.getData(frameId).setQuadrant(tempo, q);
            }
        });

        innerTableViewController.reloadFrames();
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
                Integer tempo = dataController.getTempoDivisao();
                return (tempo == null || tempo.equals(0)) ? "" : tempo.toString();
            }

            @Override
            public void didUpdateDuration(Duration oldValue, Duration newValue) {
                innerTableViewController.updateCurrentTime( ((Double) newValue.toSeconds()).intValue() );
            }

            @Override
            public String getFilePath() {
                return dataController.getVideoPath();
            }
        });
    }

}
