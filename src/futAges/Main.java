package futAges;

import futAges.controller.screenFrameWork.Screen;
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
        stage.setTitle("AGES - Futebol");
//        stage.setResizable(false);
        stage.setScene(scene);
        stage.setMinWidth(stage.getWidth());
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
