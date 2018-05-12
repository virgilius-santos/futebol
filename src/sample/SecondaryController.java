package sample;

import Main.MainController;
import Media.MediaController;
import IO.IOFiles;

import Media.MediaControllerInterface;
import Modal.FrameData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class SecondaryController implements Initializable, MediaControllerInterface {

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
    private TextField linha;
    @FXML
    private TextField coluna;
    @FXML
    private Button btnSkipForward;
    @FXML
    private Button btnPlayPause;
    @FXML
    private Button btnSkipBackward;

    @FXML
    private Label timeStamp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String video = mainController.getVideoPath();
        if (video != null) {
            mediaController = new MediaController(video,this);
        }

        linha.setText(mainController.getLinhas().toString());
        coluna.setText(mainController.getColunas().toString());

        step.setText(mainController.getTempoDivisao().toString());


        step.setOnKeyPressed( event -> {
            if(event.isControlDown() || event.isMetaDown() || event.isAltDown()) {
                event.consume();
            }

        });

        linha.setOnKeyPressed(event -> {
            if(event.isControlDown() || event.isMetaDown() || event.isAltDown()) {
                event.consume();

            }else{
                Integer value = ValidEntry(linha.getText());

                mainController.setLinhas(value);
            }

        });


        coluna.setOnKeyPressed(event -> {
            if(event.isControlDown() || event.isMetaDown() || event.isAltDown()) {
                event.consume();

            }else{
                Integer value = ValidEntry(coluna.getText());

                mainController.setColunas(value);
            }

        });

        seekSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            Integer time = mediaController.getCurrentTime();
            currentFrame = mainController.getData(time);
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



        Alert alert = new Alert(AlertType.CONFIRMATION, "Save Project", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {

            // Caminho para salvar o projeto

            File file = IOFiles.getSaveFilePath();
            mainController.setNomeProjeto(file);
            IOFiles.save(file, mainController);
        }
    }

    @FXML
    private void loadProject(ActionEvent event) { // Para carregar um projeto salvo

        Alert alert = new Alert(AlertType.CONFIRMATION, "Load Project", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {

            File file = IOFiles.getLoadFilePath();

            MainController mainController = IOFiles.load(file, MainController.class);
            if (mainController != null) {
                this.mainController = mainController;
                mediaController = new MediaController(mainController.getVideoPath(), this);

            }


        }
    }

    @FXML
    private void openPrimaryScene(ActionEvent event) { // carrega a primeira tela no evento do close project
        Stage root = Main.primaryStage;
        Alert alert = new Alert(AlertType.CONFIRMATION, "Deseja fechar o projeto?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        try {
        if(alert.getResult() == ButtonType.YES){

                Parent newParent = FXMLLoader.load(getClass().getResource("primary.fxml"));
                Scene scene =  new Scene(newParent);
                root.setScene(scene);
                root.show();
            }else{return;}
        }

        catch (IOException e) {
            e.printStackTrace();
        }
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
        Integer obj1 = Integer.parseInt(object1.getText());
        Integer obj2 = Integer.parseInt(object2.getText());
        Integer time = mediaController.getCurrentTime();

        try {
            currentFrame = new FrameData(obj1, obj2, time);
        } catch (Exception e) {
            currentFrame = null;
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


    }

    private void clearFrame() {
        object1.clear();
        object2.clear();
    }

    private Integer ValidEntry(String value) {
        Integer aux;

        try {
            aux = Integer.parseInt(value);

        } catch (NumberFormatException e) {
            return 0;
        }

        return aux;

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

    @Override
    public Button getPlayPause() {
        return this.btnPlayPause;
    }

    @Override
    public Button getSkipBackWard() {
        return this.btnSkipBackward;
    }

    @Override
    public Button getSkipForWard() {
        return this.btnSkipForward;
    }

    @Override
    public Integer timeStep() {
        Integer value = ValidEntry(step.getText());
        mainController.setTempo(value);
        return mainController.getTempoDivisao();
    }


}
