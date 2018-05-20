package main.java.controller.screenFrameWork;

import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;

import java.io.IOException;

public class Screen {

    public enum ScreenPath {
    Main("/futAges/view/Main.fxml"),
    MainPlayer("/futAges/view/MainPlayer.fxml"),
    PlayerView("/futAges/view/PlayerView.fxml"),
    TableView("/futAges/view/TableView.fxml");

        private final String valor;

        ScreenPath(String valorOpcao){
            valor = valorOpcao;
        }

        String getValor(){
            return valor;
        }
    }

    private final FXMLLoader loader;
    private Parent parent;

    public Screen(ScreenPath path) {
        loader = new FXMLLoader(getClass().getResource(path.getValor()));
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            parent = null;
        }
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public Parent getParent() {
        return parent;
    }

}
