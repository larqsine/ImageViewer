import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/App.fxml"));
        Parent root = loader.load();

        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setTitle("Image Viewer");
        Scene scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add("gui/main.css");
        primaryStage.setScene(scene);
        primaryStage.show();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        double centerXPosition = screenBounds.getMinX() + screenBounds.getWidth() / 2 - primaryStage.getWidth() / 2;
        double centerYPosition = screenBounds.getMinY() + screenBounds.getHeight() / 2 - primaryStage.getHeight() / 2;

        primaryStage.setX(centerXPosition);
        primaryStage.setY(centerYPosition);
    }
    public static void main(String[] args) {
        launch(args);
    }
}