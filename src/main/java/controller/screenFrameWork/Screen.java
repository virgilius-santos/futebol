package controller.screenFrameWork;

import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;

import java.io.IOException;

public class Screen {

    public enum ScreenPath {
    Main("/view/Main.fxml");

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
        String string = path.getValor();
        loader = new FXMLLoader(getClass().getResource(string));
        try {
            parent = loader.load();
        } catch (Exception e) {
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
