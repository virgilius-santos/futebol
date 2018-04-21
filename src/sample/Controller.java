package sample;

import Media.MediaController;
import IO.IOFiles;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.MediaView;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    private MediaController mediaController = MediaController.shared;
    private String filePath;

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
    private void videoPath(ActionEvent event) { // Para importar o vídeo

        filePath = IOFiles.getPath();
        mediaController.setMedia(filePath, mediaView);
        mediaController.setSeekSlider(seekSlider);

    }

    @FXML
    private void saveProject(ActionEvent event){ // Para salvar o projeto

        Alert alert = new Alert(AlertType.CONFIRMATION, "Save Project", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {

            // Caminho para salvar o projeto

            JFileChooser save = new JFileChooser();
            save.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            save.showSaveDialog(null);

        }
    }

    @FXML
    private void loadProject(ActionEvent event) { // Para carregar um projeto salvo

        Alert alert = new Alert(AlertType.CONFIRMATION, "Load Project", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {

            JFileChooser load = new JFileChooser();
            load.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            load.showOpenDialog(null);
            System.out.println(load.getCurrentDirectory()); // teste de console para verificar se está funcionando
            System.out.println(load.getSelectedFile()); // teste de console para verificar se está funcionando
        }
    }


    @FXML
    private void playPause(ActionEvent event) {
        mediaController.playPause();
    }

    @FXML
    private void skipForward(ActionEvent event){ // Avança o vídeo em duração pré-definida de 1,5 segundos e o pausa
        mediaController.skip(1.5,false);
    }

    @FXML
    private void skipBackward(ActionEvent event){ // Retrocede o vídeo em duração pré-definida de 1,5 segundos e o pausa
        mediaController.skip(1.5,true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListener(object1);
        addListener(object2);
        addListener(step);
    }

    private void addListener(TextField textField){

        textField.textProperty().addListener((ObservableValue<? extends String> observable,
                                              String oldValue,
                                              String newValue) -> {

            String value  = (!newValue.matches("\\d*"))
                        ? newValue.replaceAll("[^\\d]", "")
                        : newValue ;

            textField.setText( (value.length() > 5) ? value.substring(0,5) : value);


        });
    }

}

