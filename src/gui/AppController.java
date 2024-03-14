package gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AppController {
    @FXML
    public ImageView imageContainer;

    private int currentIndex = 0;
    private List<Image> images = new ArrayList<>();

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
                //Add support for multiple images or slideshow
                Image image = new Image(file.toURI().toString());
                imageContainer.setImage(image);
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

}
