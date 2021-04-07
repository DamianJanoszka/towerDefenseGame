package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {


    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane mainPane = FXMLLoader.load(getClass().getResource("/fxml/mainPane.fxml"));
        Scene scene = new Scene(mainPane);
        stage.setTitle("the Tower Defense game");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


    }
}
