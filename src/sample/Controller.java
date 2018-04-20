package sample;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    private int count = 0;

    @FXML
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;

    @FXML
    private Slider seekSlider;

    @FXML
    private void videoPath(ActionEvent event) {

        FileChooser video = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select the video(*.mp4)","*.mp4");
        video.getExtensionFilters().add(filter);
        File file = video.showOpenDialog(null);
        String filePath = file.toURI().toString();

        if (filePath != null) {
            Media media = new Media(filePath);
            this.mediaPlayer = new MediaPlayer(media);
            this.mediaView.setMediaPlayer(this.mediaPlayer);

            // Double time = mediaPlayer.getTotalDuration().toSeconds(); // Pega a duração do vídeo
            // seekSlider.setMax(time); // Define a duração do Slider como a mesma do vídeo

            this.mediaPlayer.currentTimeProperty().addListener((ObservableValue<? extends Duration> observable,Duration oldValue,Duration newValue) -> {
                seekSlider.setValue(newValue.toSeconds());
            });
            seekSlider.setOnMouseClicked(mouseEvent -> {
                mediaPlayer.seek(Duration.seconds(seekSlider.getValue()));
            });
        }
    }

    @FXML
    private void saveProject(ActionEvent event){

        Alert alert = new Alert(AlertType.CONFIRMATION, "Save Project", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {

            // Caminho para salvar o projeto

            JFileChooser save = new JFileChooser();
            save.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            save.showSaveDialog(null);

            // Método que usei para salvar o estado de um jogo no semestre passado, podemos tentar usar..

//            String savePath = "Futebol.ser";
//            try {
//                FileOutputStream fileOut = new FileOutputStream(savePath); // abre uma stream direcionada ao arquivo especifico em savePath acima
//
//                ObjectOutputStream out = new ObjectOutputStream(fileOut); // abre uma stream de objeto, para serializar, o objeto e colocar neste arquivo
//                out.writeObject(mediaPlayer); // escreve o objeto no arquivo alvo através do stream de objetos acima (ObjectOutputStream)
//
//                out.close(); // fecha a stream de objeto
//                fileOut.close(); // fecha a stream de arquivo.
//
//                // É importante sempre fechar as streams após usa-las para evitar que dados sejam corrompidos
//
//                System.out.printf("Data saved to \"" + savePath + "\""); // Verificar no console
//
//            } catch (IOException i) {  // Caso dê algum outro erro referente aos streams de gravação em arquivo
//                System.err.println(i.getMessage());
//            }
        }
    }

    @FXML
    private void loadProject(ActionEvent event) {

        Alert alert = new Alert(AlertType.CONFIRMATION, "Load Project", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {

            JFileChooser load = new JFileChooser();
            load.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            load.showOpenDialog(null);
            System.out.println(load.getCurrentDirectory());
            System.out.println(load.getSelectedFile());
        }
    }

    @FXML
    private void playPause(ActionEvent event) {

        if(count%2 == 0) {
            mediaPlayer.play();
            count++;
        } else {
            mediaPlayer.pause();
            count--;
        }
    }

    @FXML
    private void skipforward(ActionEvent event){

    }

    @FXML
    private void skipbackward(ActionEvent event){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}

