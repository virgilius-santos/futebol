package controller;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import model.entity.ProjectData;
import model.io.IOFiles;
import model.util.Conversion;
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

import static view.AgesFileChooser.*;

public class FXMLMainController implements Initializable {

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

        String md5;
        File file;
        ProjectData projectData;
        try {

            file = chooseFileToOpen(FileTypes.JSON);
            if (file == null) return;

            projectData = IOFiles.loadJsonFile(file, ProjectData.class);
            if (projectData == null) throw new Exception("arquivo de projeto invalido");

            md5 = Conversion.getMD5Checksum(projectData.getVideoFile());
            if (!projectData.getVideoMD5().equals(md5)) throw new Exception("md5 video nao confere");

            setSelectedController(innerMainPlayerViewController, projectData);
        } catch(Exception e) {
            Logger.getGlobal().log(Level.ALL, e.getMessage());
        }
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

            projectData = new ProjectData(file);

            md5 = Conversion.getMD5Checksum(file);
            projectData.setVideoMD5(md5);

            setSelectedController(innerMainPlayerViewController, projectData);

        } catch(Exception e) {
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
        if (projectData == null) return;

        File file = chooseFileToSave(FileTypes.CSV);
        if (file == null) return;

        String csv = Conversion.converter(projectData.getDados());

        IOFiles.saveCsvFile(file, csv);
    }

}
