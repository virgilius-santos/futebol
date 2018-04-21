package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream; // import de método que está comentado
import java.io.IOException; // import de método que está comentado
import java.io.ObjectOutputStream; // import de método que está comentado
import java.net.URL;
import java.util.ResourceBundle;
import Media.*;


public class Controller implements Initializable {

    private MediaController mediaController = MediaController.shared;

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

        FileChooser video = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select the video(*.mp4)","*.mp4");
        video.getExtensionFilters().add(filter);
        File file = video.showOpenDialog(null);
        String filePath = file.toURI().toString();

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
        mediaController.skip(1500.0,false);
    }

    @FXML
    private void skipBackward(ActionEvent event){ // Retrocede o vídeo em duração pré-definida de 1,5 segundos e o pausa
        mediaController.skip(1500.0,true);
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

