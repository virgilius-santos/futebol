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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class SecondaryController implements Initializable, MediaControllerInterface {

    private MediaController mediaController;
    private MainController mainController = MainController.shared;

    @FXML
    private MediaView mediaView;
    @FXML
    private Slider seekSlider;
    @FXML
    private GridPane gridPane;
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

        mainController.getDados().values().forEach(x -> addGridPaneNewRow());
        loadFrame();

        linha.setText(mainController.getLinhas().toString());
        coluna.setText(mainController.getColunas().toString());

        step.setText(mainController.getTempoDivisao().toString());

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
            clearFrame();
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
    private void saveProject(ActionEvent event) { // Para salvar o projeto
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
        String msg = "Você irá perder todas as informações não salvas, Deseja prosseguir com o fechamento do projeto?";
        Alert alert = new Alert(AlertType.CONFIRMATION, msg, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        try {
        if(alert.getResult() == ButtonType.YES) {
                mediaController.closeProject();
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

    private int getRowCount(GridPane pane) {
        int numRows = pane.getRowConstraints().size();
        for (int i = 0; i < pane.getChildren().size(); i++) {
            Node child = pane.getChildren().get(i);
            if (child.isManaged()) {
                Integer rowIndex = GridPane.getRowIndex(child);
                if(rowIndex != null){
                    numRows = Math.max(numRows,rowIndex+1);
                }
            }
        }
        return numRows;
    }

    private void addNewObject(int id) {
        FrameData obj1 = new FrameData(id, "");
        mainController.addData(obj1);
    }

    private int addGridPaneNewRow() {
        int numberOfRows = getRowCount(gridPane);
        if (numberOfRows >= 12) { return -1; }
        TextField name = new TextField();
        TextField quadrant = new TextField();
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            mainController.getData(numberOfRows).setName(newValue);
        });
        quadrant.textProperty().addListener((observable, oldValue, newValue) -> {
            Integer time = mediaController.getCurrentTime();
            if (!newValue.isEmpty()) {
                Integer q = Integer.parseInt(newValue);
                mainController.getData(numberOfRows).setQuadrant(time, q);
            }
        });
        gridPane.add(name, 0, numberOfRows);
        gridPane.add(quadrant, 1, numberOfRows);
        return numberOfRows;
    }

    @FXML
    private void addObject(ActionEvent event) {
        int id = addGridPaneNewRow();
        if (id == -1 ) return;
        addNewObject(id);
    }

    private void loadFrame(){
        Integer time = mediaController.getCurrentTime();

        int id = 0;
        int numRows = gridPane.getRowConstraints().size();
        for (int i = 0; i < gridPane.getChildren().size(); i++) {
            FrameData obj = mainController.getData(id);
            Node child = gridPane.getChildren().get(i);
            if (child.isManaged()) {
                Integer rowIndex = GridPane.getRowIndex(child);
                if(rowIndex != null) {
                    if (numRows == rowIndex + 1) {
                        if (obj.getQuadrant(time) != null) {
                            ((TextField) child).setText(obj.getQuadrant(time).toString());
                        }
                        id++;
                    } else {
                        ((TextField)child).setText(obj.getName());
                    }
                    numRows = Math.max(numRows,rowIndex + 1);
                }
            }
        }
    }

    private void clearFrame() {
        int numRows = gridPane.getRowConstraints().size();
        for (int i = 0; i < gridPane.getChildren().size(); i++) {
            Node child = gridPane.getChildren().get(i);
            if (child.isManaged()) {
                Integer rowIndex = GridPane.getRowIndex(child);
                if(rowIndex != null) {
                    if (numRows == rowIndex + 1) {
                        ((TextField)child).clear();
                    }
                    numRows = Math.max(numRows,rowIndex + 1);
                }
            }
        }
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
