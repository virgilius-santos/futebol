package controller;

import model.entity.FrameData;
import model.util.Validation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class FXMLTableViewController implements Initializable {

    public interface DataListener {
        void update(Integer frameId, String nome);
        void update(Integer frameId, Integer tempo, String quadrante);
    }

    public interface DataSource {
        int numberOfItens();
        FrameData getFrameData(Integer index);
    }

    class Location {
        private TextField name;
        private TextField quadrante;
        Location(){}

        void setName(String name) {
            if (this.name.getText().equals(name)) return;
            this.name.setText(name);
        }

        void setQuadrante(String quadrante) {
            if (this.quadrante.getText().equals(quadrante)) return;
            this.quadrante.setText(quadrante);
        }

        void clearName(){
            if (name.getText().isEmpty()) return;
            name.clear();
        }

        void clearQuadrante(){
            if (quadrante.getText().isEmpty()) return;
            quadrante.clear();
        }
    }


    private DataListener dataListener;
    private DataSource dataSource;

    private final HashMap<Integer, Location> textFieldHashMap = new HashMap<>();
    private Integer currentTime;

    @FXML
    private GridPane gridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // do nothing
    }

    // Data Listener
    void setDataListener(DataListener dataListener) {
        this.dataListener = dataListener;
    }

    private void updateDataListener(Integer index, Integer tempo, String quadrante) {
        if (dataListener == null) return;
        dataListener.update(index, tempo, quadrante);
    }

    private void updateDataListener(Integer index, String nome) {
        if (dataListener == null) return;
        dataListener.update(index, nome);
    }

    // Data source
    void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private int numberOfItens() {
        if (dataSource == null) return 0;
        return dataSource.numberOfItens();
    }

    private FrameData getFrameData(Integer index) {
        if (dataSource == null) return null;
        return dataSource.getFrameData(index);
    }



    void updateCurrentTime(Integer newTime) {
        if (currentTime != null && currentTime.equals(newTime)) return;
        currentTime = newTime;
        updateRows();
    }

    void cleanTable() {
        textFieldHashMap.clear();
        gridPane.getChildren().clear();
    }

    void loadFrames() {
        for (int index = 0; index < numberOfItens(); index++) {
            insertRow(index);
        }
    }

    void insertRow(int index) {
        Location location = createRow(index);
        updateRowValue(index, location);
    }

    private Location getRow(int index) {
        return textFieldHashMap.get(index);
    }

    private void updateRows() {
        Location location;
        for (int index = 0; index < numberOfItens(); index++) {
            location = getRow(index);
            updateRowValue(index, location);
        }
    }

    private void updateRowValue(int id, Location location) {

        FrameData frameData = getFrameData(id);
        if (frameData == null) {
            location.clearName();
            location.clearQuadrante();
            return;
        }

        Integer quadrante = frameData.getQuadrant(currentTime);
        if (quadrante != null) {
            location.setQuadrante(quadrante.toString());
        } else {
            location.clearQuadrante();
        }

        String name = frameData.getName();
        if (name != null) {
            location.setName(name);
        } else {
            location.clearName();
        }

    }

    private Location createRow(int index) {

        int columnNameIndex = 0;
        int columnQuadranteIndex = 1;

        Location newLocation = new Location();
        textFieldHashMap.put(index, newLocation);

        addNewTextFieldName(columnNameIndex, index);
        addNewTextQuadrant(columnQuadranteIndex, index);
        return newLocation;
    }

    private void addNewTextFieldName(int column, int index) {
        TextField nameTextField = new TextField();
        nameTextField.setOnKeyPressed(Validation::onKeyPressed);
        nameTextField.textProperty().addListener((observable, oldValue, newValue) -> updateDataListener(index, newValue));
        nameTextField.setFocusTraversable(false);

        textFieldHashMap.get(index).name = nameTextField;
        gridPane.add(nameTextField, column, index);
    }

    private void addNewTextQuadrant(int column, int index) {
        TextField quadrantTextField = new TextField();
        quadrantTextField.setOnKeyPressed(Validation::onKeyPressed);
        quadrantTextField.setOnKeyTyped(evt -> Validation.onKeyTyped(evt, 6));
        quadrantTextField.textProperty().addListener((observable, oldValue, newValue) -> updateDataListener(index, currentTime, newValue));

        textFieldHashMap.get(index).quadrante = quadrantTextField;
        gridPane.add(quadrantTextField, column, index);
    }

}
