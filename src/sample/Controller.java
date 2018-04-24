package sample;

import Main.MainController;
import Media.MediaController;
import IO.IOFiles;

import Media.MediaControllerInterface;
import Modal.FrameData;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable, MediaControllerInterface {

    private MediaController mediaController;
    private MainController mainController = MainController.shared;
    private FrameData currentFrame = null;

    @FXML
    private MediaView mediaView;
    @FXML
    private Slider seekSlider;
    @FXML
    private TextField object1;
    @FXML
    private TextField object2;
    @FXML
    private TextField step;
    @FXML
    private TextArea obs;
    @FXML
    private TextField linha;
    @FXML
    private TextField coluna;

    @FXML
    private Label timeStamp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        linha.setText(mainController.getLinhas().toString());
        coluna.setText(mainController.getColunas().toString());

        step.setText(mainController.getTempoDivisao().toString());
        step.setOnKeyReleased(event -> mainController.setTempo(Integer.parseInt(step.getText())));

        linha.setOnKeyReleased(event -> mainController.setLinhas(Integer.parseInt(linha.getText())));
        coluna.setOnKeyReleased(event -> mainController.setColunas(Integer.parseInt(coluna.getText())));

        seekSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            currentFrame = mainController.getData(newValue.intValue());
            loadFrame();

        });

    }
    @FXML
    private void videoPath(ActionEvent event) { // Para importar o vídeo
        String filePath = IOFiles.getVideoPath().toURI().toString();
        mainController.setVideoPath(filePath);
        mediaController = new MediaController(filePath, this);
    }

    @FXML
    private void saveProject(ActionEvent event){ // Para salvar o projeto



        Alert alert = new Alert(AlertType.CONFIRMATION, "Save Project", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {

            // Caminho para salvar o projeto

            File file = IOFiles.getSaveFilePath();
            mainController.setNomeProjeto(file.getName());
            IOFiles.save(file, mainController);
        }
    }

    @FXML
    private void loadProject(ActionEvent event) { // Para carregar um projeto salvo

        Alert alert = new Alert(AlertType.CONFIRMATION, "Load Project", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {

            File file = IOFiles.getLoadFilePath();

            mainController = IOFiles.load(file, MainController.class);
            
        }
    }


    @FXML
    private void playPause(ActionEvent event) {
        mediaController.playPause();
    }

    @FXML
    private void skipForward(ActionEvent event){ // Avança o vídeo em duração pré-definida de 1,5 segundos e o pause
        mediaController.skip(mainController.getTempoDivisao(),false);
    }

    @FXML
    private void skipBackward(ActionEvent event){ // Retrocede o vídeo em duração pré-definida de 1,5 segundos e o pause
        mediaController.skip(mainController.getTempoDivisao(),true);
    }

    @FXML
    private void textFielKeyTyped(KeyEvent event) {
        TextField textField = (TextField) event.getSource();
        String c = event.getCharacter();

        if (textField.getText().length() > 5 || !c.matches("\\d*")) {
            event.consume();
        }
    }

    @FXML
    private void saveFrame(ActionEvent event) {
        Double obj1 = Double.parseDouble(object1.getText());
        Double obj2 = Double.parseDouble(object2.getText());
        String obs = this.obs.getText();
        Integer time = mediaController.getCurrentTime();

        if (currentFrame == null) {
            //FrameData(Double obj1, Double obj2, String obs, Integer time)
            try {
                currentFrame = new FrameData(obj1, obj2, obs, time);
            } catch (Exception e) {
                currentFrame = null;
            }
        }

        if (currentFrame != null) {
            mainController.addData(currentFrame, time);
        }

    }

    private void loadFrame(){
        if (currentFrame == null) {
            clearFrame();
            return;
        }

        if (currentFrame.getObj1() != null) object1.setText(currentFrame.getObj1().toString());
        if (currentFrame.getObj2() != null) object2.setText(currentFrame.getObj2().toString());
        if (currentFrame.getObs() != null) obs.setText(currentFrame.getObs());
    }

    private void clearFrame() {
        obs.clear();
        object1.clear();
        object2.clear();
    }


    @Override
    public Label getLabel() {
        return this.timeStamp;
    }

    @Override
    public MediaView getMediaView() {
        return this.mediaView;
    }

    @Override
    public Slider getSlider() {
        return this.seekSlider;
    }
}

