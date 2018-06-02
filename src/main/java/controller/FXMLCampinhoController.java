package controller;



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
    private javafx.scene.image.ImageView soccerField;
    @FXML
    private TextField nbRowsField;
    @FXML
    private TextField nbColumnsField;
    @FXML
    private GridPane gridPane;
    @FXML
    private javafx.scene.control.Label rows;
    @FXML
    private javafx.scene.control.Label columns;
    @FXML
    private javafx.scene.image.ImageView SoccerField;

    private int linhas = 10;
    int colunas = 10;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JvmMemManagerEntryMeta tilePane;



        gridPane.resize(511, 323);
        for (int i = 0; i < linhas; i++) {
            RowConstraints rConstraint = new RowConstraints();
            rConstraint.setPercentHeight(100 / linhas);
            gridPane.getRowConstraints().add(rConstraint);
        }

        for (int i = 0; i < colunas; i++) {
            ColumnConstraints cConstraint = new ColumnConstraints();
            cConstraint.setPercentWidth(100 / colunas);
            gridPane.getColumnConstraints().add(cConstraint);
        }

    }


}

