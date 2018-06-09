package controller;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import javafx.scene.control.ButtonBar;
import javafx.scene.media.MediaException;
import model.entity.ProjectData;
import model.io.IOFiles;
import model.util.Conversion;
import model.util.Validation;
import view.AgesFileChooser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static model.util.Conversion.getMD5Checksum;
import static model.util.Validation.isValidateVideo;
import static view.AgesFileChooser.*;

public class FXMLMainController implements Initializable {

    class MD5Exception extends Exception {}

    public interface ControlledScreen {
        void setProjectData(ProjectData projectData);
        void screenDidDisappear();
    }

    private ProjectData projectData;
    private ControlledScreen selectedController;

    @FXML
    private FXMLProjectController innerMainPlayerViewController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // do nothing
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

        selectedController.screenDidDisappear();
        projectData = null;

        System.exit(0);

    }

    /**
     * abre um novo projeto a partir de um arquivo json
     * Excessao gerada caso o projeto esteja corrompido
     * Excessao gerada se o MD5 do video nao é igual ao MD5 armazenado
     */
    @FXML
    private void handleMenuItemFileLoad() {

        File file;
        ProjectData projectData;
        try {

            file = chooseFileToOpen(FileTypes.JSON);
            if (file == null) return;

            projectData = IOFiles.loadJsonFile(file, ProjectData.class);
            if (projectData == null) throw new Exception("arquivo de projeto invalido");

            handleFileOpenProjectData(projectData);
        }  catch (Exception e) {
            Logger.getGlobal().log(Level.ALL, e.getMessage());
        }
    }

    private void handleFileOpenProjectData(ProjectData projectData){
        String md5;
        try {

            isValidateVideo(projectData.getVideoURI());

            md5 = getMD5Checksum(projectData.getVideoFile());
            if (!projectData.getVideoMD5().equals(md5)) throw new MD5Exception();

            if (projectData.getProjetoFile() != null) {
                IOFiles.saveJsonFile(projectData.getProjetoFile(), projectData);
            }
            
            setSelectedController(innerMainPlayerViewController, projectData);
        } catch (Validation.MediaException|MD5Exception e) {
            handleFileFindVideoFile(projectData);
        }  catch (Exception e) {
            Logger.getGlobal().log(Level.ALL, e.getMessage());
        }
    }

    private void handleFileFindVideoFile( ProjectData projectData) {
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
                if (file == null) throw new Exception("Arquivo nao selecionado");

                projectData.setVideoFile(file);
                handleFileOpenProjectData(projectData);

            } catch (Exception e) {
                Logger.getGlobal().log(Level.ALL, e.getMessage());
            }

            return;
        }

        export(projectData);

    }

    /** abre um novo projeto a partir da seleçao de um video
     *
     */
    @FXML
    private void handleMenuItemFileNew() {

        File file;
        String md5;
        ProjectData projectData;
        try {
            file = chooseFileToOpen(FileTypes.VIDEO);
            if (file == null) throw new Exception("arquivo de video nao encontrado");

            isValidateVideo(file.toURI().toString());

            projectData = new ProjectData(file);

            md5 = getMD5Checksum(file);
            projectData.setVideoMD5(md5);

            setSelectedController(innerMainPlayerViewController, projectData);

        } catch(MediaException e) {
            String msg = "Formato Invalido";
            Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.NO);
            alert.showAndWait();
        }catch(Exception e) {
            Logger.getGlobal().log(Level.ALL, e.getMessage());
        }
    }


    /**
     * fecha o projeto exibicao e carrega o novo projeto
     *
     * @param selectedController eh a tela que sera exibida
     * @param projectData eh o projeto que sera usado durante a exibicao
     */
    private void setSelectedController(ControlledScreen selectedController, ProjectData projectData) {
        if (this.selectedController != null) this.selectedController.screenDidDisappear();
        this.projectData = projectData;
        this.selectedController = selectedController;
        this.selectedController.setProjectData(projectData);
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
     * caso exista um projeto em andamento, permite exportar esse projeto no formato csv
     */
    @FXML
    private void handleMenuItemFileExport() {
        export(this.projectData);
    }

    private void export(ProjectData projectData){
        if (projectData == null) return;

        File file = chooseFileToSave(FileTypes.CSV);
        if (file == null) return;

        String csv = Conversion.converter(projectData.getDados());

        IOFiles.saveCsvFile(file, csv);
    }

}
