package futAges;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception{
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("view/Main.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("AGES - Futebol");
//        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        /**  scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent doubleClicked) {
        if (doubleClicked.getClickCount() == 2){
        stage.setFullScreen(true);
        }
        }
        });
         */
    }

    public static void main(String[] args) {
        launch(args);
    }
}
