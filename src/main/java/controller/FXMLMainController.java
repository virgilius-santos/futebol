package controller;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ButtonBar;
import javafx.scene.media.MediaException;
import model.entity.ProjectData;
import model.io.IOFiles;
import model.io.ScreenLoader;
import model.util.Validation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static model.util.Conversion.convertToCSV;
import static model.util.Conversion.getMD5CheckSum;
import static model.util.Validation.isValidateVideo;
import static view.AgesFileChooser.*;

public class FXMLMainController implements Initializable {

    private class MD5Exception extends Exception {
        private static final long serialVersionUID = 844737862139211806L;
    }
    private class GenericException extends Exception {
        private static final long serialVersionUID = 4451357718847766601L;

        GenericException(String msg){
            super(msg);
        }
    }

    public interface ControlledScreen {
        void setProjectData(ProjectData projectData);
        void screenDidDisappear();
    }

    private ProjectData projectData;
    private ControlledScreen projectScreen;
    private ControlledScreen soccerFieldScreen;

    @FXML
    private FXMLProjectController innerMainPlayerViewController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // do nothing
    }

    /**
     * abre a tela de visualização do campinho
     */
    @FXML
    private void handleMenuItemOpenField() {

        try {
            if (projectData == null) throw new Exception("Primeiro abra/carregue um projeto");

            if (soccerFieldScreen != null) {
                soccerFieldScreen.screenDidDisappear();
                return;
            }

            ScreenLoader screen = new ScreenLoader(ScreenLoader.ScreenPath.SOCCERFIELD);

            Parent root = screen.getParent();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);

            stage.setOnShown(evt -> {
                soccerFieldScreen = screen.getLoader().getController();
                soccerFieldScreen.setProjectData(projectData);
            });

            stage.setOnCloseRequest(evt -> soccerFieldScreen = null);

            stage.show();


        } catch (IOException e) {
            // erro na leitura do fxml
            Logger.getGlobal().log(Level.ALL, e.getMessage());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    /** abre um novo projeto a partir da seleçao de um video
     *
     */
    @FXML
    private void handleMenuItemFileNew() {

        File file;
        String md5;
        try {
            file = chooseFileToOpen(FileTypes.VIDEO);
            if (file == null) throw new GenericException("arquivo de video nao encontrado");

            isValidateVideo(file.toURI().toString());

            ProjectData data = new ProjectData(file);

            md5 = getMD5CheckSum(file);
            data.setVideoMD5(md5);

            setSelectedController(innerMainPlayerViewController, data);

        } catch(MediaException e) {
            String msg = "Formato Invalido";
            Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.NO);
            alert.showAndWait();
        }catch(GenericException|NoSuchAlgorithmException|IOException|Validation.MediaException e) {
            Logger.getGlobal().log(Level.ALL, e.getMessage());
        }
    }

    /**
     * abre um novo projeto a partir de um arquivo json
     * Excessao gerada caso o projeto esteja corrompido
     * Excessao gerada se o MD5 do video nao é igual ao MD5 armazenado
     */
    @FXML
    private void handleMenuItemFileLoad() {

        File file;
        try {

            file = chooseFileToOpen(FileTypes.JSON);
            if (file == null) return;

            ProjectData data = IOFiles.loadJsonFile(file, ProjectData.class);
            if (data == null) throw new GenericException("arquivo de projeto invalido");

            handleValidateProjectData(data);
        }  catch (GenericException e) {
            Logger.getGlobal().log(Level.ALL, e.getMessage());
        }
    }


    /**
     * salva um projeto no endereco definido previamente
     * caso nao exista o arquivo chama o metodo save as...
     */
    @FXML
    private void handleMenuItemFileSave() {
        if (projectData == null) return;

        if (projectData.getProjetoFile() == null){
            handleMenuItemFileSaveAs();
            return;
        }

        IOFiles.saveJsonFile(projectData.getProjetoFile(), projectData);
    }

    /**
     * abre a caixa de selecao e permite salvar um projeto no formato JSon
     */
    @FXML
    private void handleMenuItemFileSaveAs() {
        if (projectData == null) return;

        File file = chooseFileToSave(FileTypes.JSON);
        if (file == null) return;

        projectData.setProjetoFile(file);
        IOFiles.saveJsonFile(file, projectData);
    }

    /**
     * acao do menu item exportar
     */
    @FXML
    private void handleMenuItemFileExport() {
        export(this.projectData);
    }

    /**
     * fecha o projeto e avisa que os dados nao foram salvos
     */
    @FXML
    private void handleMenuItemFileClose() {

        if(projectData == null) { System.exit(0); }

        String msg = "Você irá perder todas as informações não salvas, \nDeseja prosseguir com o fechamento do projeto?";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, msg, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.NO) return;

        projectScreen.screenDidDisappear();
        projectData = null;

        System.exit(0);

    }

    /**
     * valida o formato do video
     * valida o md5 do video
     * salva o projeto se existir um diretorio para isso
     * chama o metodo para visualizar o projeto
     *
     * @param projectData objeto com os dados do projeto
     */
    private void handleValidateProjectData(ProjectData projectData){
        String md5;
        try {

            isValidateVideo(projectData.getVideoURI());

            md5 = getMD5CheckSum(projectData.getVideoFile());
            if (!projectData.getVideoMD5().equals(md5)) throw new MD5Exception();

            if (projectData.getProjetoFile() != null) {
                IOFiles.saveJsonFile(projectData.getProjetoFile(), projectData);
            }

            setSelectedController(innerMainPlayerViewController, projectData);
        } catch (Validation.MediaException|MD5Exception e) {
            handleFindVideoFile(projectData);
        }  catch (Exception e) {
            Logger.getGlobal().log(Level.ALL, e.getMessage());
        }
    }

    /**
     * informa q o video é invalido
     * abre opcao para o usuario:
     * - exportar os dados do projeto caso nao posssua o video original
     * - localizar o video original
     *
     * @param projectData objeto com os dados do projeto
     */
    private void handleFindVideoFile( ProjectData projectData) {
        String msg = "Arquivo de video Invalido, \npode localizar o arquivo do video \nou exportar dados do projeto";
        ButtonType localizar = new ButtonType("Localizar", ButtonBar.ButtonData.YES);
        ButtonType exportar = new ButtonType("Exportar", ButtonBar.ButtonData.NO);
        Alert alert = new Alert(Alert.AlertType.ERROR, msg,
                ButtonType.CANCEL,
                localizar,
                exportar);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.CANCEL) return;

        if(alert.getResult() == localizar) {
            File file;
            try {
                file = chooseFileToOpen(FileTypes.VIDEO);
                if (file == null) throw new GenericException("Arquivo nao selecionado");

                projectData.setVideoFile(file);
                handleValidateProjectData(projectData);

            } catch (GenericException e) {
                Logger.getGlobal().log(Level.ALL, e.getMessage());
            }

            return;
        }

        if(alert.getResult() == localizar) {
            export(projectData);
        }
    }

    /**
     * Permite que o usuario escolha um local para salvar os dados do projeto em csv
     *
     * @param projectData objeto com os dados do projeto
     */
    private void export(ProjectData projectData){
        if (projectData == null) return;

        File file = chooseFileToSave(FileTypes.CSV);
        if (file == null) return;

        String csv = convertToCSV(projectData.getDados());

        IOFiles.saveCsvFile(file, csv);
    }


    /**
     * fecha o projeto exibicao e carrega o novo projeto
     *
     * @param selectedController eh a tela que sera exibida
     * @param projectData eh o projeto que sera usado durante a exibicao
     */
    private void setSelectedController(ControlledScreen selectedController, ProjectData projectData) {
        if (this.soccerFieldScreen != null) soccerFieldScreen.screenDidDisappear();
        if (this.projectScreen != null) this.projectScreen.screenDidDisappear();
        this.projectData = projectData;
        this.projectScreen = selectedController;
        this.projectScreen.setProjectData(projectData);
    }


}
