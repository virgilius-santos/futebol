package futAges.controller;

import futAges.model.Entity.FrameData;
import futAges.model.Util.Validation;
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
        FrameData getData(Integer index);
        int createData();
    }

    class Location {
        private TextField name;
        private TextField quadrante;
        Location(){}
    }


    private DataListener dataListener;
    private DataSource dataSource;

    private HashMap<Integer, Location> textFieldHashMap = new HashMap<>();
    private Integer currentTime;

    @FXML
    private GridPane gridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    void setDataListener(DataListener dataListener) {
        this.dataListener = dataListener;
    }

    private void updateDataListener(Integer frameId, Integer tempo, String quadrante) {
        if (dataListener == null) return;
        dataListener.update(frameId, tempo, quadrante);
    }

    private void updateDataListener(Integer frameId, String nome) {
        if (dataListener == null) return;
        dataListener.update(frameId, nome);
    }



    void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private int numberOfItens() {
        if (dataSource == null) return 0;
        return dataSource.numberOfItens();
    }

    private FrameData getData(Integer index) {
        if (dataSource == null) return null;
        return dataSource.getData(index);
    }

    void reloadFrames() {
        for (int index = 0; index < numberOfItens(); index++) {
            addGridPaneNewRow(index);
            loadFrame(index);
        }
    }

    private void addGridPaneNewRow(int index) {

        int columnNameIndex = 0;
        int columnQuadranteIndex = 1;

        textFieldHashMap.put(index, new Location());

        addNewTextFieldName(columnNameIndex, index);
        addNewTextQuadrant(columnQuadranteIndex, index);
    }

    private void addNewTextFieldName(int column, int index) {
        TextField nameTextField = new TextField();
        nameTextField.setOnKeyPressed(Validation::onKeyPressed);
        nameTextField.textProperty().addListener((observable, oldValue, newValue) -> updateDataListener(index, newValue));

        textFieldHashMap.get(index).name = nameTextField;
        gridPane.add(nameTextField, column, index);
    }

    private void addNewTextQuadrant(int column, int index) {
        TextField quadrantTextField = new TextField();
        quadrantTextField.setOnKeyTyped(Validation::onKeyTyped);
        quadrantTextField.textProperty().addListener((observable, oldValue, newValue) -> updateDataListener(index, currentTime, newValue));

        textFieldHashMap.get(index).quadrante = quadrantTextField;
        gridPane.add(quadrantTextField, column, index);
    }


    private void loadFrame(int id) {
        Location location = textFieldHashMap.get(id);
        loadFrame(id, location);
    }

    private void loadFrame(int id, Location location) {

        FrameData obj = getData(id);
        if (obj == null) {
            location.name.clear();
            location.quadrante.clear();
            return;
        }

        Integer quadrante = obj.getQuadrant(currentTime);
        if (quadrante != null) {
            location.quadrante.setText(quadrante.toString());
        } else {
            location.quadrante.clear();
        }

        String name = obj.getName();
        if (name != null) {
            if (!name.equals(location.name.getText())) location.name.setText(name);
        } else {
            location.name.clear();
        }

    }


    void updateCurrentTime(Integer newTime) {
        if (currentTime != null && currentTime.equals(newTime)) return;
        currentTime = newTime;
        reloadFrames();
    }

    @FXML
    private void addObject() {
        int index = dataSource.createData();
        addGridPaneNewRow(index);
        loadFrame(index);
    }
}
