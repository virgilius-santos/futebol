package controller;


import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import model.util.Validation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import sun.management.snmp.jvmmib.JvmMemManagerEntryMeta;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLCampinhoController implements Initializable {

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
        while (gridPane.getRowConstraints().size() > 0) {
            gridPane.getRowConstraints().remove(0);
        }

        while (gridPane.getColumnConstraints().size() > 0) {
            gridPane.getColumnConstraints().remove(0);
        }
    }

    private void addGridRowsAndColums() {
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
        if (text.isEmpty()) { return; }
        linesQnt = Integer.parseInt(text);
        clearGridPane();
        addGridRowsAndColums();
    }

    private void setColumnsQuantity(String text) {
        if (text.isEmpty()) { return; }
        columnsQnt = Integer.parseInt(text);
        clearGridPane();
        addGridRowsAndColums();
    }

    @FXML
    private void textFieldOnKeyPressed(KeyEvent event) {
        Validation.onKeyPressed(event);
    }

    @FXML
    private void textFieldKeyTyped(KeyEvent event) {
        Validation.onKeyTyped(event);
    }
}
