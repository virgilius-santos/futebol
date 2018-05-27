package controller;

import controller.FXMLMainController.ControlledScreen;
import model.entity.FrameData;
import model.entity.ProjectData;
import model.util.Conversion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLProjectController implements Initializable, ControlledScreen {

    private ProjectData projectData;

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
                return projectData.getDataSize();
            }

            @Override
            public FrameData getFrameData(Integer index) {
                return projectData.getData(index);
            }

        });

        innerTableViewController.setDataListener(new FXMLTableViewController.DataListener() {
            @Override
            public void update(Integer index, String nome) {
                projectData.getData(index).setName(nome);
            }

            @Override
            public void update(Integer index, Integer tempo, String quadrante) {
                Integer q = (quadrante == null || quadrante.isEmpty()) ? null : Integer.parseInt(quadrante);
                projectData.getData(index).setQuadrant(tempo, q);
            }
        });

    }

    private void configureMediaPlayer() {
        innerPlayerViewController.setMediaPlayerDataSource(new FXMLPlayerViewController.PlayerDataSource() {
            @Override
            public void didStepUpdated(String step) {
                Integer tempo = Conversion.stringToInt(step);
                projectData.setTempoDivisao(tempo);
            }

            @Override
            public String getCurrentStep() {
                return projectData.getTempoDivisao().toString();
            }

            @Override
            public void didUpdateDuration(Duration newValue) {
                innerTableViewController.updateCurrentTime(Conversion.getSeconds(newValue));
            }

            @Override
            public String getFilePath() {
                return projectData.getVideoURI();
            }
        });
    }

    @Override
    public void setProjectData(ProjectData projectData) {
        this.projectData = projectData;

        innerPlayerViewController.loadMedia();
        innerTableViewController.loadFrames();

        innerPlayerView.getScene().setOnKeyPressed( e -> {
            if (e.getCode() == KeyCode.ENTER) {
                int index = projectData.addData(new FrameData());
                if (index != -1) innerTableViewController.insertRow(index);
            }
        });
    }

    @Override
    public void screenDidDisappear() {
        innerPlayerViewController.closePlayer();
        innerTableViewController.cleanTable();
    }





}
