package controller;


import controller.FXMLMainController.ControlledScreen;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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

    private ProjectData projectData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nbRowsField.textProperty().addListener((observable, oldValue, newValue) -> setLinesQuantity(newValue));
        nbColumnsField.textProperty().addListener((observable, oldValue, newValue) -> setColumnsQuantity(newValue));
//        addGridRowsAndColumns();
    }

    @FXML
    private void textFieldOnKeyPressed(KeyEvent event) {
        Validation.onKeyPressed(event);
    }

    @FXML
    private void textFieldKeyTyped(KeyEvent event) {
        Validation.onKeyTyped(event, 1);
    }

    @Override
    public void setProjectData(ProjectData projectData) {
        if (projectData == null) return;

        this.projectData = projectData;

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

        for (int i = 0; i < getLines() ; i++) {
            RowConstraints rConstraint = new RowConstraints();
            rConstraint.setVgrow(Priority.ALWAYS);
            gridPane.getRowConstraints().add(rConstraint);
        }

        for (int i = 0; i < getColumns(); i++) {
            ColumnConstraints cConstraint = new ColumnConstraints();
            cConstraint.setHgrow(Priority.ALWAYS);
            gridPane.getColumnConstraints().add(cConstraint);
        }
    }

    private void createLabelList() {

        List<Label> labels = new LinkedList<>();
        Label label;
        int indice = 1;

        for (int i = 0; i < getLines() ; i++) {
            for (int j = 0; j < getColumns(); j++) {
                label = new Label(String.valueOf(indice));
                indice++;

                GridPane.setRowIndex(label, i);
                GridPane.setColumnIndex(label, j);
                GridPane.setHalignment(label, HPos.CENTER);
                GridPane.setValignment(label, VPos.CENTER);
                GridPane.setFillWidth(label, true);

                label.setMinSize(getWidth(), getHeight());
                label.setMaxSize(getWidth(), getHeight());
                label.setTextAlignment(TextAlignment.CENTER);
                label.setAlignment(Pos.CENTER);

                label.setTextFill(Color.web("#ffffff"));
                label.setFont(Font.font("Comic Sans", FontWeight.BOLD, 20));

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

    public double getWidth() {
        return gridPane.getWidth() / getColumns();
    }

    public double getHeight() {
        return gridPane.getHeight() / getLines();
    }

    public double getLines() {
        return (projectData != null) ? projectData.getLines() : 100;
    }

    public double getColumns() {
        return (projectData != null) ? projectData.getColumns() : 100;
    }
}
