package gui;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AppController {
    @FXML
    public ImageView imageContainer;
    @FXML
    public BorderPane mainBorderPane;

    private Stage stage;

    private int currentIndex = 0;
    private List<Image> images = new ArrayList<>();
    private boolean slideshowRunning = false;
    private Thread slideshowThread;

    public void LoadImagesButton(ActionEvent actionEvent) {
        // Create a file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image Files");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        // Show the file chooser dialog
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);

        // Load selected images
        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                Image image = new Image(file.toURI().toString());
                imageContainer.setImage(image);
                images.add(image);
            }
        }
    }
    public void NextButton(ActionEvent actionEvent) {
        currentIndex++;
        if (currentIndex >= images.size()) {
            currentIndex = 0;
        }
        Image imageToShow = images.get(currentIndex);
        imageContainer.setImage(imageToShow);
    }
    public void PreviousButton(ActionEvent actionEvent) {
        currentIndex--;

        if (currentIndex < 0) {
            currentIndex = images.size() - 1;
        }

        // Display the image corresponding to the currentIndex
        Image imageToShow = images.get(currentIndex);
        imageContainer.setImage(imageToShow);
    }
    @FXML
    private void SlideshowButton(ActionEvent actionEvent) {
        slideshowRunning=true;
        slideshowThread = new Thread(() -> {
            while (slideshowRunning) {
                try {
                    Thread.sleep(2000); // Display each image for 2 seconds
                    Platform.runLater(() -> NextButton(actionEvent)); // Go to the next image
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        slideshowThread.setDaemon(true);
        slideshowThread.start();
    }

    public void Init(Stage stage) {
        this.stage = stage;

        RespondToWidthAndHeightChanges();

        stage.maximizedProperty().addListener((observable, oldValue, newValue) -> RespondToWidthAndHeightChanges());
        stage.widthProperty().addListener((obs, oldVal, newVal) -> RespondToWidthAndHeightChanges());
        stage.heightProperty().addListener((obs, oldVal, newVal) -> RespondToWidthAndHeightChanges());
    }

    private void RespondToWidthAndHeightChanges() {
        double sceneWidth = stage.getScene().getWidth();
        setPaneHeight(mainBorderPane, stage.getScene().getHeight());
        setPaneWidth(mainBorderPane, sceneWidth);

        imageContainer.setFitWidth(stage.getWidth());
        imageContainer.setFitHeight(stage.getHeight() - 50);
        imageContainer.setPreserveRatio(true);
    }

    private void setPaneHeight(Pane pane, double value) {
        pane.setMinHeight(value);
        pane.setPrefHeight(value);
        pane.setMaxHeight(value);
    }
    private void setPaneWidth(Pane pane, double value) {
        pane.setMinWidth(value);
        pane.setPrefWidth(value);
        pane.setMaxWidth(value);
    }

}

