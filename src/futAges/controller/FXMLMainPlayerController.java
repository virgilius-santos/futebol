package futAges.controller;

import futAges.controller.screenFrameWork.ControlledScreen;
import futAges.model.Entity.FrameData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FXMLMainPlayerController implements Initializable, ControlledScreen {

    private DataController dataController;
    private List<FrameData> values;

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
        updateValues();

        configureTableView();

        configureMediaPlayerStep();
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
                return values.size();
            }

            @Override
            public FrameData getData(Integer index) {
                return values.get(index);
            }

            @Override
            public int createData() {
                int index = values.size();
                dataController.addData(new FrameData());
                updateValues();
                return index;
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

    private void configureMediaPlayerStep() {
        innerPlayerViewController.setStep(dataController.getTempoDivisao());
    }

    private void configureMediaPlayer() {
        innerPlayerViewController.setMediaPlayer(dataController.getVideoPath());
        innerPlayerViewController.setMediaPlayerListener((observable, oldValue, newValue) ->
                innerTableViewController.updateCurrentTime( ((Double) newValue.toSeconds()).intValue() )
        );
    }

    private void updateValues(){
        values = dataController.getDados()
                .values()
                .stream().sorted(
                        (e1, e2) -> (e1.getId() > e2.getId()) ? 1 : ((e1.getId() < e2.getId()) ? -1 : 0)
                )
                .collect(Collectors.toList());
    }
}
