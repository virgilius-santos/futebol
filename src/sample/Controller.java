package sample;

import Main.MainController;
import Media.MediaController;
import IO.IOFiles;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaView;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    private MediaController mediaController = MediaController.shared;
    private MainController mainController = MainController.shared;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        step.setText(mainController.getTempoDivisao().toString());
        step.setOnKeyReleased(event -> mainController.setTempo(Integer.parseInt(step.getText())));
    }
    @FXML
    private void videoPath(ActionEvent event) { // Para importar o vídeo

        filePath = IOFiles.getPath();
        mainController.setVideoPath(filePath);
        mediaController.setMedia(filePath, mediaView);
        mediaController.setSeekSlider(seekSlider);

    }

    @FXML
    private void saveProject(ActionEvent event){ // Para salvar o projeto

//        Alert alert = new Alert(AlertType.CONFIRMATION, "Save Project", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
//        alert.showAndWait();
//
//        if (alert.getResult() == ButtonType.YES) {
//
//            // Caminho para salvar o projeto
//
//            JFileChooser save = new JFileChooser();
//            save.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//            save.showSaveDialog(null);
//
//        }
    }

    @FXML
    private void loadProject(ActionEvent event) { // Para carregar um projeto salvo

//        Alert alert = new Alert(AlertType.CONFIRMATION, "Load Project", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
//        alert.showAndWait();
//
//        if (alert.getResult() == ButtonType.YES) {
//
//            JFileChooser load = new JFileChooser();
//            load.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//            load.showOpenDialog(null);
//            System.out.println(load.getCurrentDirectory()); // teste de console para verificar se está funcionando
//            System.out.println(load.getSelectedFile()); // teste de console para verificar se está funcionando
//        }
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

}

