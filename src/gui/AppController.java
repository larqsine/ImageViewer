package gui;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppController {
    @FXML
    public ImageView imageContainer;
    @FXML
    public BorderPane mainBorderPane;
    @FXML
    public TextField filenameTextField;
    @FXML
    public TextField redCountTextField;
    @FXML
    public TextField greenCountTextField;
    @FXML
    public TextField blueCountTextField;

    private Stage stage;
    private ExecutorService executorService;

    private int currentIndex = 0;
    private List<Image> images = new ArrayList<>();
    private boolean slideshowRunning = false;
    private Thread slideshowThread;

    public void LoadImagesButton(ActionEvent actionEvent) {

        //We have imported free images from Google to the data folder within this project.
        //Feel free to copy path and use them :)

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image Files");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);

        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                Image image = new Image(file.toURI().toString());
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
        updateUI(imageToShow);
    }

    public void PreviousButton(ActionEvent actionEvent) {
        currentIndex--;

        if (currentIndex < 0) {
            currentIndex = images.size() - 1;
        }

        Image imageToShow = images.get(currentIndex);
        updateUI(imageToShow);
    }

    public void startSlideshowButton(ActionEvent actionEvent) {
        slideshowRunning = true;
        Task<Void> slideshowTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (slideshowRunning) {
                    Thread.sleep(2000);
                    Platform.runLater(() -> NextButton(actionEvent));
                }
                return null;
            }
        };
        slideshowThread = new Thread(slideshowTask);
        slideshowThread.setDaemon(true);
        slideshowThread.start();
    }

    public void stopSlideshowButton(ActionEvent actionEvent) {
        slideshowRunning = false;
    }

    public void Init(Stage stage) {
        this.stage = stage;
        this.executorService = Executors.newSingleThreadExecutor();

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

    private void updateUI(Image image) {
        executorService.submit(() -> {
            CountColors(image);
            Platform.runLater(() -> {
                imageContainer.setImage(image);
                filenameTextField.setText(images.get(currentIndex).getUrl());
            });
        });
    }

    private void CountColors(Image image) {
        executorService.submit(() -> {
            int redCount = 0;
            int greenCount = 0;
            int blueCount = 0;

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    Color color = image.getPixelReader().getColor(x, y);
                    if (color.getRed() > color.getGreen() && color.getRed() > color.getBlue()) {
                        redCount++;
                    } else if (color.getGreen() > color.getRed() && color.getGreen() > color.getBlue()) {
                        greenCount++;
                    } else if (color.getBlue() > color.getRed() && color.getBlue() > color.getGreen()) {
                        blueCount++;
                    }
                }
            }
            int finalRedCount = redCount;
            int finalGreenCount = greenCount;
            int finalBlueCount = blueCount;
            Platform.runLater(() -> {
                redCountTextField.setText(String.valueOf(finalRedCount));
                greenCountTextField.setText(String.valueOf(finalGreenCount));
                blueCountTextField.setText(String.valueOf(finalBlueCount));
            });
        });
    }
}