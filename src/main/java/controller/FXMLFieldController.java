package controller;


import controller.FXMLMainController.ControlledScreen;
import javafx.scene.input.KeyEvent;
import model.entity.ProjectData;
import model.util.Validation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import java.net.URL;
import java.util.ResourceBundle;
import model.util.Conversion;

public class FXMLFieldController implements Initializable, ControlledScreen {

    @FXML
    private TextField nbRowsField;
    @FXML
    private TextField nbColumnsField;
    @FXML
    private GridPane gridPane;

    private int linesQnt = 0;
    private int columnsQnt = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nbRowsField.textProperty().addListener((observable, oldValue, newValue) -> setLinesQuantity(newValue));
        nbColumnsField.textProperty().addListener((observable, oldValue, newValue) -> setColumnsQuantity(newValue));
    }

    private void clearGridPane() {
        gridPane.getRowConstraints().clear();
        gridPane.getColumnConstraints().clear();
    }

    private void addGridRowsAndColumns() {
        for (int i = 0; i < linesQnt ; i++) {
            RowConstraints rConstraint = new RowConstraints();
            rConstraint.setPrefHeight(gridPane.getHeight() / linesQnt);
            rConstraint.setPercentHeight(100);
            gridPane.getRowConstraints().add(rConstraint);
        }

        for (int i = 0; i < columnsQnt; i++) {
            ColumnConstraints cConstraint = new ColumnConstraints();
            cConstraint.setPrefWidth(gridPane.getHeight() / columnsQnt);
            cConstraint.setPercentWidth(100);
            gridPane.getColumnConstraints().add(cConstraint);
        }
    }

    private void setLinesQuantity(String text) {
        linesQnt = Conversion.stringToInt(text);
        clearGridPane();
        addGridRowsAndColumns();
    }

    private void setColumnsQuantity(String text) {
        columnsQnt = Conversion.stringToInt(text);
        clearGridPane();
        addGridRowsAndColumns();
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
        linesQnt = projectData.getLines();
        columnsQnt = projectData.getColumns();
    }

    @Override
    public void screenDidDisappear() { }
}
