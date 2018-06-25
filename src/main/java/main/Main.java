package main;

import model.io.ScreenLoader;
import model.io.ScreenLoader.ScreenPath;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception{


        primaryStage = stage;
        ScreenLoader screen = new ScreenLoader(ScreenPath.MAIN);

        Parent root = screen.getParent();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sports Analytics");
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
