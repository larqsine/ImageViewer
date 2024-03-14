import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
public class Main extends Application {

    public void start(Stage primaryStage) throws Exception {


        Parent root = FXMLLoader.load(getClass().getResource(""));
        primaryStage.setTitle("ImageViewer beta");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);


    }
}