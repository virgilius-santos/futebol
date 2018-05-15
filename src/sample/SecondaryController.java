package sample;

import futAges.Main;
import futAges.controller.DataController;

import Media.MediaController;
import futAges.modal.IO.IOFiles;

import Media.MediaControllerInterface;
import futAges.modal.Entity.FrameData;
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
    private DataController dataController = new DataController();

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

        // Setup data - Inicializar um FrameData para cada linha da tabela
        if (dataController.getDados().isEmpty()) {
            FrameData obj1 = new FrameData(1, "Obj1");
            FrameData obj2 = new FrameData(2, "Obj2");
            dataController.addData(obj1);
            dataController.addData(obj2);
        }

        String video = dataController.getVideoPath();
        if (video != null) {
            mediaController = new MediaController(video,this);
        }

        linha.setText(dataController.getLinhas().toString());
        coluna.setText(dataController.getColunas().toString());

        step.setText(dataController.getTempoDivisao().toString());
        loadFrame();

        step.setOnKeyPressed( event -> {
            if (event.isControlDown() || event.isMetaDown() || event.isAltDown()) {
                event.consume();
            }
        });

        linha.setOnKeyPressed(event -> {
            if (event.isControlDown() || event.isMetaDown() || event.isAltDown()) {
                event.consume();

            } else {
                Integer value = ValidEntry(linha.getText());

                dataController.setLinhas(value);
            }
        });


        coluna.setOnKeyPressed(event -> {
            if(event.isControlDown() || event.isMetaDown() || event.isAltDown()) {
                event.consume();

            }else{
                Integer value = ValidEntry(coluna.getText());

                dataController.setColunas(value);
            }

        });

        seekSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            clearFrame();
            loadFrame();
        });
    }

    @FXML
    private void videoPath(ActionEvent event) { // Para importar o vídeo
        String filePath = IOFiles.getVideoPath().toURI().toString();
        dataController.setVideoPath(filePath);
        mediaController = new MediaController(filePath, this);
    }

    @FXML
    private void saveProject(ActionEvent event) { // Para salvar o projeto
        Alert alert = new Alert(AlertType.CONFIRMATION, "Save Project", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {

            // Caminho para salvar o projeto

            File file = IOFiles.getSaveFilePath();
            dataController.setNomeProjeto(file);
            IOFiles.save(file, dataController);
        }
    }

    @FXML
    private void loadProject(ActionEvent event) { // Para carregar um projeto salvo

        Alert alert = new Alert(AlertType.CONFIRMATION, "Load Project", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {

            File file = IOFiles.getLoadFilePath();

            DataController dataController = IOFiles.load(file, DataController.class);
            if (dataController != null) {
                this.dataController = dataController;
                mediaController = new MediaController(dataController.getVideoPath(), this);

            }
        }
    }

    @FXML
    private void openPrimaryScene(ActionEvent event) { // carrega a primeira tela no evento do close project
        Stage root = Main.primaryStage;
        String msg = "Você irá perder todas as informações não salvas, Deseja prosseguir com o fechamento do projeto?";
        Alert alert = new Alert(AlertType.CONFIRMATION, msg, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        try {
        if(alert.getResult() == ButtonType.YES) {

                Parent newParent = FXMLLoader.load(getClass().getResource("primary.fxml"));
                Scene scene =  new Scene(newParent);
                root.setScene(scene);
                root.show();
            } else {return;}
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
        Integer quadrant1 = Integer.parseInt(object1.getText());
        Integer quadrant2 = Integer.parseInt(object2.getText());
        Integer time = mediaController.getCurrentTime();

        // Id deve ser o número da linha da tabela
        FrameData obj1 = dataController.getData(1);
        FrameData obj2 = dataController.getData(2);

        obj1.setQuadrant(time, quadrant1);
        obj2.setQuadrant(time, quadrant2);
    }

    private void loadFrame(){
        Integer time = mediaController.getCurrentTime();

        // Id deve ser o número da linha da tabela
        FrameData obj1 = dataController.getData(1);
        FrameData obj2 = dataController.getData(2);

        if (obj1.getQuadrant(time) != null) {
            object1.setText(obj1.getQuadrant(time).toString());
        }
        
        if (obj2.getQuadrant(time) != null) {
            object2.setText(obj2.getQuadrant(time).toString());
        }

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
        dataController.setTempo(value);
        return dataController.getTempoDivisao();
    }


}
