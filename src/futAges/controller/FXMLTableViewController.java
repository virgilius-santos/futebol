package futAges.controller;

import futAges.model.Entity.FrameData;
import futAges.model.Util.Validation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class FXMLTableViewController implements Initializable {

    private Map<Integer, FrameData> dados;
    private Integer currentTime;

    @FXML
    private GridPane gridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void loadFrame(){
        Integer time = currentTime;

        int id = 0;
        int numRows = gridPane.getRowConstraints().size();
        for (int i = 0; i < gridPane.getChildren().size(); i++) {
            FrameData obj = dados.get(id);
            if (obj == null) {
                obj = new FrameData(id);
                dados.put(id, obj);
            }
            Node child = gridPane.getChildren().get(i);
            if (child.isManaged()) {
                Integer rowIndex = GridPane.getRowIndex(child);
                if(rowIndex != null) {
                    if (numRows == rowIndex + 1) {
                        if (obj.getQuadrant(time) != null) {
                            ((TextField) child).setText(obj.getQuadrant(time).toString());
                        }
                        id++;
                    } else {
                        ((TextField)child).setText(obj.getName());
                    }
                    numRows = Math.max(numRows,rowIndex + 1);
                }
            }
        }
    }

    @FXML
    private void addObject(ActionEvent event) {
        int id = addGridPaneNewRow();
        if (id == -1 ) return;
        addNewObject(id);
    }

    private void addNewObject(int id) {
        FrameData obj1 = new FrameData(id, "");
        dados.put(obj1.getId(), obj1);
    }

    private int addGridPaneNewRow() {
        int numberOfRows = getRowCount(gridPane);
        if (numberOfRows >= 12) { return -1; }
        addNewTextFieldName(0, numberOfRows);
        addNewTextQuadrant(1, numberOfRows);
        return numberOfRows;
    }

    private void addNewTextFieldName(int column, int index) {
        TextField name = new TextField();
        name.setOnKeyPressed(evt -> Validation.onKeyPressed(evt));
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            dados.get(index).setName(newValue);
        });
        gridPane.add(name, column, index);
    }

    private void addNewTextQuadrant(int column, int index) {
        TextField quadrant = new TextField();
        quadrant.setOnKeyTyped( evt -> Validation.onKeyTyped(evt));
        quadrant.textProperty().addListener((observable, oldValue, newValue) -> {
            Integer time = currentTime;
            if (!newValue.isEmpty()) {
                Integer q = Integer.parseInt(newValue);
                dados.get(index).setQuadrant(time, q);
            }
        });
        gridPane.add(quadrant, column, index);
    }

    private int getRowCount(GridPane pane) {
        int numRows = pane.getRowConstraints().size();
        for (int i = 0; i < pane.getChildren().size(); i++) {
            Node child = pane.getChildren().get(i);
            if (child.isManaged()) {
                Integer rowIndex = GridPane.getRowIndex(child);
                if(rowIndex != null){
                    numRows = Math.max(numRows,rowIndex+1);
                }
            }
        }
        return numRows;
    }

    private void clearFrame() {
        int numRows = gridPane.getRowConstraints().size();
        for (int i = 0; i < gridPane.getChildren().size(); i++) {
            Node child = gridPane.getChildren().get(i);
            if (child.isManaged()) {
                Integer rowIndex = GridPane.getRowIndex(child);
                if(rowIndex != null) {
                    if (numRows == rowIndex + 1) {
                        ((TextField)child).clear();
                    }
                    numRows = Math.max(numRows,rowIndex + 1);
                }
            }
        }
    }

    public void setData(Map<Integer, FrameData> data) {
        dados = data;
        if (dados.values().isEmpty()) {
            addGridPaneNewRow();
            addGridPaneNewRow();
        } else {
            dados.values().forEach(x -> addGridPaneNewRow());
        }

        loadFrame();
    }

    public void updateCurrentTime(Integer newTime) {
        currentTime = newTime;
        clearFrame();
        loadFrame();
    }
}
