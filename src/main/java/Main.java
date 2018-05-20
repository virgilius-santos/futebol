package main.java;

import main.java.controller.screenFrameWork.Screen;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception{

        primaryStage = stage;

        Parent root = (new Screen(Screen.ScreenPath.Main)).getParent();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("AGES - Futebol");
        stage.show();

        double height = stage.getHeight();
        double width = stage.getWidth();
        stage.setMinWidth(width);
        stage.setMinHeight(height);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
