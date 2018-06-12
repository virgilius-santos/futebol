package model.io;

import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;

import java.io.IOException;

public class ScreenLoader {

    public enum ScreenPath {
    MAIN("/view/Main.fxml"),
    SOCCERFIELD("/view/Field.fxml");

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

    public ScreenLoader(ScreenPath path) throws IOException {
        String string = path.getValor();

        loader = new FXMLLoader(getClass().getResource(string));

        parent = loader.load();

    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public Parent getParent() {
        return parent;
    }

}
