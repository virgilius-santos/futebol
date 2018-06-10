package controller;


import controller.FXMLMainController.ControlledScreen;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.entity.ProjectData;
import model.util.Validation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import model.util.Conversion;

public class FXMLFieldController implements Initializable, ControlledScreen {

    @FXML
    private TextField nbRowsField;
    @FXML
    private TextField nbColumnsField;
    @FXML
    private GridPane gridPane;

    private List<Label> labelList = new LinkedList<>();

    private double width = 65;
    private double height = 65;
    private ProjectData projectData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nbRowsField.textProperty().addListener((observable, oldValue, newValue) -> setLinesQuantity(newValue));
        nbColumnsField.textProperty().addListener((observable, oldValue, newValue) -> setColumnsQuantity(newValue));
    }

    @FXML
    private void textFieldOnKeyPressed(KeyEvent event) {
        Validation.onKeyPressed(event);
    }

    @FXML
    private void textFieldKeyTyped(KeyEvent event) {
        Validation.onKeyTyped(event, 2);
    }

    @Override
    public void setProjectData(ProjectData projectData) {
        this.projectData = projectData;

        width = gridPane.getWidth() / projectData.getColumns();
        height = gridPane.getHeight() / projectData.getLines();

        nbRowsField.setText(projectData.getLines().toString());
        nbColumnsField.setText(projectData.getColumns().toString());

        addGridRowsAndColumns();
    }

    @Override
    public void screenDidDisappear() {
        Stage stage = (Stage) gridPane.getScene().getWindow();
        stage.show();
        stage.toFront();
    }

    private void setLinesQuantity(String text) {
        int value = Conversion.stringToInt(text);
        if (this.projectData.getLines() == value) return;

        this.projectData.setLines(value);
        addGridRowsAndColumns();
    }

    private void setColumnsQuantity(String text) {
        int value = Conversion.stringToInt(text);
        if (this.projectData.getColumns() == value) return;

        this.projectData.setColumns(value);
        addGridRowsAndColumns();
    }

    private void addGridRowsAndColumns() {

        clearGridPane();
        createLabelList();

        for (int i = 0; i < projectData.getLines() ; i++) {
            RowConstraints rConstraint = new RowConstraints();
            rConstraint.setVgrow(Priority.ALWAYS);
            gridPane.getRowConstraints().add(rConstraint);
        }

        for (int i = 0; i < projectData.getColumns(); i++) {
            ColumnConstraints cConstraint = new ColumnConstraints();
            cConstraint.setHgrow(Priority.ALWAYS);
            gridPane.getColumnConstraints().add(cConstraint);
        }
    }

    private void createLabelList() {

        List<Label> labels = new LinkedList<>();
        Label label;
        int indice = 1;

        for (int i = 0; i < projectData.getLines() ; i++) {
            for (int j = 0; j < projectData.getColumns(); j++) {
                label = new Label(String.valueOf(indice));
                indice++;

                GridPane.setRowIndex(label, i);
                GridPane.setColumnIndex(label, j);
                GridPane.setHalignment(label, HPos.CENTER);
                GridPane.setValignment(label, VPos.CENTER);
                GridPane.setFillWidth(label, true);

                label.setMinSize(width, height);
                label.setMaxSize(width, height);
                label.setTextAlignment(TextAlignment.CENTER);
                label.setAlignment(Pos.CENTER);

                label.setTextFill(Color.web("#bf0000"));

                labels.add(label);
                gridPane.getChildren().add(label);
            }
        }
        labelList = labels;
    }

    private void clearGridPane() {
        gridPane.getChildren().removeAll(labelList);
        labelList.clear();
        gridPane.getRowConstraints().clear();
        gridPane.getColumnConstraints().clear();
    }
}
