package main;

import controller.FXMLMainController;
import model.io.ScreenLoader;
import model.io.ScreenLoader.ScreenPath;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {



    @Override
    public void start(Stage stage) throws Exception{



        ScreenLoader screen = new ScreenLoader(ScreenPath.MAIN);
        ((FXMLMainController) screen.getLoader().getController()).setPrimaryStage(stage);

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
